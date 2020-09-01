package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.request.UserDetailsRequestModel;
import pl.devzyra.model.response.UserRest;
import pl.devzyra.services.UserService;

import javax.validation.Valid;

import static pl.devzyra.exceptions.ErrorMessages.INCORRECT_FIELDS;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails, BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalStateException(INCORRECT_FIELDS.getErrorMessage());
        }

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        UserRest returnVal = modelMapper.map(createdUser, UserRest.class);

        return ResponseEntity.ok(returnVal);

    }


    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getSpecificUser(@PathVariable String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        UserRest returnVal = modelMapper.map(userDto, UserRest.class);

        return ResponseEntity.ok(returnVal);
    }



}
