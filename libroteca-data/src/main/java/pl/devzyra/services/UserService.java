package pl.devzyra.services;

import org.springframework.stereotype.Repository;
import pl.devzyra.model.dto.UserDto;

@Repository
public interface UserService {

    UserDto createUser(UserDto userDto);

}
