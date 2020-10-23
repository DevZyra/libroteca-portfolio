package pl.devzyra.restcontrollers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.model.response.AddressRest;
import pl.devzyra.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("rest/addresses")
public class AddressRestController {

    private final AddressService addressService;

    public AddressRestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressRest> getAddressesByUserId() {

        return addressService.getAllAddresses();

    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressRest> getAddressesByUserId(@PathVariable String userId) {

        List<AddressRest> addresses = addressService.getAddressesByUserId(userId);

        return addresses;
    }

}
