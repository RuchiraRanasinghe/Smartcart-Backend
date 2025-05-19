package smartcart.org.service;

import smartcart.org.dto.LoginRequestDto;
import smartcart.org.dto.LoginResponseDto;
import smartcart.org.dto.RegisterRequestDto;
import smartcart.org.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(RegisterRequestDto registerDto);

    LoginResponseDto login(LoginRequestDto loginDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto update(Long id, UserDto userDto);

    boolean deleteById(Long id);
}