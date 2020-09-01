package pl.devzyra.services;


import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.devzyra.exceptions.ErrorMessages;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.dto.AddressDto;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.repositories.UserRepository;
import pl.devzyra.utils.Utils;

import static pl.devzyra.exceptions.ErrorMessages.*;
import static pl.devzyra.exceptions.ErrorMessages.RECORD_ALREADY_EXISTS;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Utils utils;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Utils utils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.utils = utils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserServiceException(RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        if (user.getAddresses().size() != 0) {

            for (int i = 0; i < user.getAddresses().size(); i++) {
                AddressDto address = user.getAddresses().get(i);
                address.setUserDetails(user);
                address.setAddressId(utils.generateAddressId(20));
                user.getAddresses().set(i, address);
            }

        }

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setUserId(utils.generateUserId(15));
        userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmailVerificationStatus(false);

        UserEntity stored = userRepository.save(userEntity);

        UserDto returnVal = modelMapper.map(stored, UserDto.class);

        return returnVal;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());
        }
        UserDto returnVal = modelMapper.map(userEntity, UserDto.class);
        return returnVal;
    }


    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }


}
