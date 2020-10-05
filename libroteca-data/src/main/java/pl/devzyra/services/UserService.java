package pl.devzyra.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.entities.UserEntity;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto) throws UserServiceException;

    UserDto getUserByUserId(String userId) throws UserServiceException;

    UserDto updateUser(String userId, UserDto userDto) throws UserServiceException;

    void deleteUser(String userId) throws UserServiceException;

    List<UserDto> getUsers(int page, int limit);

    UserEntity getUserByEmail(String email);
}
