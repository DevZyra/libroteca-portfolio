package pl.devzyra.restcontrollers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.response.AddressRest;
import pl.devzyra.services.AddressService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("rest/addresses")
public class AddressRestController {

    private final AddressService addressService;

    public AddressRestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<AddressRest> getAllAddresses() {


        List<AddressRest> allAddresses = addressService.getAllAddresses();

        Link self = linkTo(methodOn(AddressRestController.class).getAllAddresses()).withSelfRel();
        Link addressByUser = linkTo(AddressRestController.class).slash("/{userId}").withRel("/{userId}");

        return CollectionModel.of(allAddresses, List.of(self, addressByUser));

    }


    @Secured("ROLE_ADMIN")
    @GetMapping(value = "{userId}/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<AddressRest> getUserAddresses(@PathVariable String userId) throws UserServiceException {

        List<AddressRest> addresses = addressService.getAddressesByUserId(userId);

        Link selfLink = linkTo(methodOn(AddressRestController.class).getUserAddresses(userId)).withSelfRel();
        Link user = linkTo(UserRestController.class).slash(userId).withRel("user");
        Link allUsers = linkTo(methodOn(UserRestController.class).getAllUsers(0, 25)).withRel("users");

        return CollectionModel.of(addresses, List.of(selfLink, user, allUsers));
    }
}
