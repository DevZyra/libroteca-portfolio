package pl.devzyra.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String firstName;
    @NotEmpty(message = "{NotEmpty}")
    private String lastName;
    @NotEmpty(message = "{NotEmpty}")
    @Email(message = "{Email}")
    private String email;

    @NotEmpty(message = "{NotEmpty}")
    @Size(min = 5, max = 30, message = "{Size}")
    private String password;
    @Valid
    private List<AddressRequestModel> addresses = new ArrayList<>();


}
