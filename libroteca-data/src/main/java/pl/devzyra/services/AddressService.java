package pl.devzyra.services;

import pl.devzyra.model.response.AddressRest;

import java.util.List;

public interface AddressService {

    List<AddressRest> getAddressesByUserId(String userId);

    List<AddressRest> getAllAddresses();
}
