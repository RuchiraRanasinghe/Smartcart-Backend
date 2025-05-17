package smartcart.org.service;

import smartcart.org.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto update(Long id, UserDto userDto);

    boolean deleteById(Long id);
}
