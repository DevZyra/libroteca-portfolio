package pl.devzyra.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import pl.devzyra.services.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getSpecificUser() throws Exception {

        mockMvc.perform(
                get("/rest/users/user")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
        )
                .andExpect(status().isOk());
    }

    @Test
    void getSpecificUser_ByNonAuthorised() throws Exception {

        mockMvc.perform(
                get("/rest/users/user")
                        .with(user("user").roles("USER"))
                        .with(csrf())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllUser_ByAuthorisedUser() throws Exception {

        mockMvc.perform(
                get("/rest/users")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
        )
                .andExpect(status().isOk());
    }

    @Test
    void getUsers_NonAuthorised_ThrowsAccessDeniedException() throws Exception {
        mockMvc.perform(
                get("/rest/users")
                        .with(user("user").roles("USER"))
                        .with(csrf())
        )
                .andExpect(status().isForbidden());
    }

}