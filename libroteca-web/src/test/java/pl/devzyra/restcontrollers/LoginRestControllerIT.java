package pl.devzyra.restcontrollers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import pl.devzyra.filters.JwtUtils;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.request.UserLoginRequest;
import pl.devzyra.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
@TestPropertySource(properties = {"DNS=localhost"})
class LoginRestControllerIT {

    @LocalServerPort
    int port;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existentUserCanGetTokenAndAuthentication() throws Exception {
        String login_url = "http://localhost:" + port + "/rest/login";

        UserLoginRequest userLoginRequest = new UserLoginRequest("admin", "admin");


        String json = restTemplate.postForObject(login_url, userLoginRequest, String.class);
        UserEntity user = userRepository.findByEmail(userLoginRequest.getUsername());

        assertTrue(jwtUtils.validateToken(json, user));
        assertNotNull(json);
    }
}