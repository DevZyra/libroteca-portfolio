package pl.devzyra.services;

import org.springframework.stereotype.Repository;
import pl.devzyra.model.dto.UserDto;

import java.util.List;

@Repository
public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page , int limit);
}
