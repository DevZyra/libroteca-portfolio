package pl.devzyra.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDetailsRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String firstName;
    @NotEmpty(message = "{NotEmpty}")
    private String lastName;
    @Email(message = "{Email}")
    private String email;

    // TODO: more complex password validation
    @NotEmpty(message = "{NotEmpty}")
    @Size(min = 5,max = 30,message = "{Size}")
    private String password;

    private List<AddressRequestModel> addresses = new ArrayList<>();

}
