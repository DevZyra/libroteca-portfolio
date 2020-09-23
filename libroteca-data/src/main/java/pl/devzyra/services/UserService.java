package pl.devzyra.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.devzyra.model.dto.UserDto;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page, int limit);
}
