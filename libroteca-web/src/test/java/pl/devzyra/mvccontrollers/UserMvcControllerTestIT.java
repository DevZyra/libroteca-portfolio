package pl.devzyra.mvccontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.devzyra.model.request.UserLoginRequest;
import pl.devzyra.services.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"DNS=localhost"})
class UserMvcControllerTestIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    private static final String USERFORM = "signupform";
    private static final String LOGIN = "login";

    @BeforeEach
    void setUp() {
        new UserMvcController(userService, modelMapper);
    }

    @Test
    void initUserCreationForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERFORM));
    }

    @Test
    void logUser() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN));

    }

    @Test
    void processUserCreation() throws Exception {

        mockMvc.perform(post("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERFORM));
    }


}