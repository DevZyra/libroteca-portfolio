package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.request.UserDetailsRequestModel;
import pl.devzyra.model.response.UserRest;
import pl.devzyra.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static pl.devzyra.exceptions.ErrorMessages.INCORRECT_FIELDS;

@RestController
@RequestMapping("rest/users")
public class UserRestController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails, BindingResult result) throws UserServiceException {

        if (result.hasErrors()) {
            throw new IllegalStateException(INCORRECT_FIELDS.getErrorMessage());
        }

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        UserRest returnVal = modelMapper.map(createdUser, UserRest.class);

        return ResponseEntity.ok(returnVal);

    }
    @Secured("ROLE_ADMIN")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "25") int limit) {

        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        users.stream().forEach(x -> {
            UserRest userModel = modelMapper.map(x, UserRest.class);
            returnValue.add(userModel);
        });

        return returnValue;
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getSpecificUser(@PathVariable String userId) throws UserServiceException {

        UserDto userDto = userService.getUserByUserId(userId);

        UserRest returnVal = modelMapper.map(userDto, UserRest.class);

        return ResponseEntity.ok(returnVal);
    }
    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String userId) throws UserServiceException {


        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto updatedUser = userService.updateUser(userId, userDto);

        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);

        return ResponseEntity.ok(returnValue);
    }
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) throws UserServiceException {

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
