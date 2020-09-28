package pl.devzyra.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.filters.JwtUtils;
import pl.devzyra.model.entities.UserLoginRequest;
import pl.devzyra.services.UserService;

import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {


    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login-rest")
    public ResponseEntity<?> generateToken(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));


        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(userLoginRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + jwt);
        return ResponseEntity.ok(jwt);

    }

}
