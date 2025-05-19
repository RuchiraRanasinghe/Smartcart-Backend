package smartcart.org.service;

import smartcart.org.dto.*;

import java.util.List;

public interface AuthService {

    UserDto create(RegisterRequestDto registerDto);

    LoginResponseDto login(LoginRequestDto loginDto);

    void forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);

    void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto update(Long id, UserDto userDto);

    boolean deleteById(Long id);

}