package pl.devzyra.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String zipCode;

    private UserDto userDetails;
}
