package pl.devzyra.mvccontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.request.UserDetailsRequestModel;
import pl.devzyra.services.UserService;

import javax.validation.Valid;


@Controller
public class UserMvcController {

    private final String VIEW_USER_FORM = "signupform";

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserMvcController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/signup")
    public String initUserCreationForm(Model model) {
        model.addAttribute("user", new UserDetailsRequestModel());

        return VIEW_USER_FORM;
    }

    @PostMapping("/users/new")
    public String processUserCreation(@Valid @ModelAttribute("user") UserDetailsRequestModel user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_USER_FORM;
        }

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.createUser(userDto);

        return "index";
    }
}
