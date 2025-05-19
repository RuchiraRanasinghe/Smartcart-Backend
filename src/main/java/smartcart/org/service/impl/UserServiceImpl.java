package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import smartcart.org.dto.LoginRequestDto;
import smartcart.org.dto.LoginResponseDto;
import smartcart.org.dto.RegisterRequestDto;
import smartcart.org.dto.UserDto;
import smartcart.org.entity.User;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.exception.UserAlreadyExistsException;
import smartcart.org.repository.UserRepository;
import smartcart.org.security.JwtTokenProvider;
import smartcart.org.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found with id: ";
    private static final String USERNAME_EXISTS_MESSAGE = "Username already exists: ";
    private static final String EMAIL_EXISTS_MESSAGE = "Email already exists: ";

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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + loginDto.getUsername()));
        return new LoginResponseDto(token, modelMapper.map(user, UserDto.class));
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
    public boolean deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE + id);
        }
        userRepository.deleteById(id);
        return true;
    }
}