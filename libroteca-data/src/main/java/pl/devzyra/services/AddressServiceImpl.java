package pl.devzyra.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.devzyra.model.entities.AddressEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.response.AddressRest;
import pl.devzyra.repositories.AddressRepository;
import pl.devzyra.repositories.UserRepository;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AddressRest> getAddressesByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);

        List<AddressEntity> addresses = user.getAddresses();

        Type listType = new TypeToken<List<AddressRest>>() {
        }.getType();

        return modelMapper.map(addresses, listType);
    }

    @Override
    public List<AddressRest> getAllAddresses() {
        List<AddressEntity> all = (List<AddressEntity>) addressRepository.findAll();

        Type listType = new TypeToken<List<AddressRest>>() {
        }.getType();

        return modelMapper.map(all, listType);
    }
}
