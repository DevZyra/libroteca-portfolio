package pl.devzyra.model.response;

import lombok.Data;


@Data
public class AddressRest {

    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String zipCode;

}
