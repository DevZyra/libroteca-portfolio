package pl.devzyra.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class AddressRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String city;
    @NotEmpty(message = "{NotEmpty}")
    private String country;
    private String streetName;
    @Pattern(regexp = "^[a-zA-Z0-9]{6}", message = "{Zipcode}")
    private String zipCode;


    public AddressRequestModel() {
    }

}
