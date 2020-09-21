package pl.devzyra.mvccontrollers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.devzyra.model.dto.AddressDto;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.request.AddressRequestModel;
import pl.devzyra.model.request.UserDetailsRequestModel;
import pl.devzyra.services.UserService;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;


@Controller
public class UserMvcController {

    private final String VIEW_USER_FORM = "signupform";
    private final String SIGNUP_CONFIRM = "signupconfirm";
    private final String LOGIN_PAGE = "login";

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

    @GetMapping("/login")
    public String logUser() {


        return LOGIN_PAGE;
    }


    @PostMapping("/users")
    public String processUserCreation(@Valid @ModelAttribute("user") UserDetailsRequestModel user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return VIEW_USER_FORM;
        }

        List<AddressRequestModel> addresses = user.getAddresses();

        Type listType = new TypeToken<List<AddressDto>>() {
        }.getType();
        List<AddressDto> dtos = modelMapper.map(addresses, listType);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setAddresses(dtos);

        userService.createUser(userDto);

        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);


        return SIGNUP_CONFIRM;
    }
}
