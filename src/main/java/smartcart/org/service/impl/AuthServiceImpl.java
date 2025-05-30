package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import smartcart.org.dto.ForgotPasswordRequestDto;
import smartcart.org.dto.LoginRequestDto;
import smartcart.org.dto.LoginResponseDto;
import smartcart.org.dto.RegisterRequestDto;
import smartcart.org.dto.ResetPasswordRequestDto;
import smartcart.org.dto.UserDto;
import smartcart.org.entity.PasswordResetToken;
import smartcart.org.entity.User;
import smartcart.org.exception.*;
import smartcart.org.repository.PasswordResetTokenRepository;
import smartcart.org.repository.UserRepository;
import smartcart.org.security.JwtTokenProvider;
import smartcart.org.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender mailSender;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found with id: ";
    private static final String USERNAME_EXISTS_MESSAGE = "Username already exists: ";
    private static final String EMAIL_EXISTS_MESSAGE = "Email already exists: ";
    private static final int OTP_LENGTH = 6;
    private static final long OTP_EXPIRY_MINUTES = 10;
    private static final Random RANDOM = new Random();

    @Override
    public UserDto create(RegisterRequestDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new UserAlreadyExistsException(USERNAME_EXISTS_MESSAGE + registerDto.getUsername());
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UserAlreadyExistsException(EMAIL_EXISTS_MESSAGE + registerDto.getEmail());
        }

        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setActive(true);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            User user = userRepository.findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + loginDto.getUsername()));
            return new LoginResponseDto(token, modelMapper.map(user, UserDto.class));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid User name Or Password");
        }
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        User user = userRepository.findByEmail(forgotPasswordRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + forgotPasswordRequestDto.getEmail()));

        tokenRepository.deleteByUserId(user.getId());

        String otp = generateOtp();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
        PasswordResetToken resetToken = new PasswordResetToken(otp, user, expiryDate);
        tokenRepository.save(resetToken);

        sendOtpEmail(user.getEmail(), otp);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        PasswordResetToken resetToken = tokenRepository.findByToken(resetPasswordRequestDto.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid or expired OTP"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new InvalidTokenException("OTP has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE + id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE + id));
        if (!existingUser.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException(USERNAME_EXISTS_MESSAGE + userDto.getUsername());
        }
        if (!existingUser.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(EMAIL_EXISTS_MESSAGE + userDto.getEmail());
        }
        modelMapper.map(userDto, existingUser);
        return modelMapper.map(userRepository.save(existingUser), UserDto.class);
    }

    @Override
    public Boolean deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE + id);
        }
        userRepository.deleteById(id);
        return true;
    }

    private String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(RANDOM.nextInt(10));
        }
        return otp.toString();
    }

    private void sendOtpEmail(String toEmail, String otp) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Password Reset OTP - SmartCart POS");
            helper.setText("Your OTP for password reset is: <b>" + otp + "</b><br>This OTP is valid for " + OTP_EXPIRY_MINUTES + " minutes.", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Failed to send OTP email", e);
        }
    }
}