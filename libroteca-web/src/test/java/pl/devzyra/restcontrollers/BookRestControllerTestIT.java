package pl.devzyra.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
@TestPropertySource(properties = {"DNS=localhost"})
class BookRestControllerTestIT {

    private static String path = "";


    @LocalServerPort
    int port;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        path = "http://localhost:" + port + "/rest/books";
        restTemplate = new RestTemplate();
    }


    @Test
    void getBookById() {

        ResponseEntity<String> response = restTemplate.
                getForEntity(path + "/1", String.class);

        String body = response.getBody();
        String titleToCheck = "Book";

        assertNotNull(body);
        assertTrue(body.contains(titleToCheck));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllBooks() {

        ResponseEntity<String> response = restTemplate.
                getForEntity(path, String.class);

        String body = response.getBody();

        assertNotNull(body);
        assertTrue(body.contains("Book"));
        assertTrue(body.contains("isbn"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}