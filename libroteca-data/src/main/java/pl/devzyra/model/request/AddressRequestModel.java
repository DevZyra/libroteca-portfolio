package pl.devzyra.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AddressRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String city;
    @NotEmpty(message = "{NotEmpty}")
    private String country;
    private String streetName;
    @Pattern(message = "{code.valid}", regexp = "\\d{2}-\\d{3}")
    private String zipcode;

    
    public AddressRequestModel() {
    }

}
