package pl.devzyra.services;

import pl.devzyra.model.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page , int limit);
}
