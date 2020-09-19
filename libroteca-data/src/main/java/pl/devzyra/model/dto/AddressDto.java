package pl.devzyra.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AddressDto implements Serializable {

    private static final long serialVersionUID = -3432666704228581164L;

    private Long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String zipCode;


    private UserDto userDetails;

    public AddressDto() {
    }
}
