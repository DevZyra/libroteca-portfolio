package pl.devzyra.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.dto.AddressDto;
import pl.devzyra.model.dto.UserDto;
import pl.devzyra.model.entities.AddressEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.repositories.UserRepository;
import pl.devzyra.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    Utils utils;
    @Mock
    PasswordEncoder passwordEncoder;

    private static final String userId = "auia2f2de";
    private static final String password = "pazzNcOde11";
    UserEntity userEntity;
    UserDto userDto;


    @BeforeEach
    void setUp() throws UserServiceException {
        MockitoAnnotations.initMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Anny");
        userEntity.setLastName("Manny");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword(password);
        userEntity.setUserId(userId);
        userEntity.setAddresses(getAddressesEntities());

        userDto = new UserDto();
        userDto.setFirstName("Anny");
        userDto.setLastName("Manny");
        userDto.setEmail("anny@manny.com");
        userDto.setUserId(userId);
        userDto.setEncryptedPassword(password);
        userDto.setAddresses(getAddressesDto());


    }

    @Test
    void createUser() throws UserServiceException {

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("aAbBcc111");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(passwordEncoder.encode(anyString())).thenReturn(password);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);
        UserDto storedUserDetails = userService.createUser(userDto);
        storedUserDetails.setUserId(utils.generateUserId(anyInt()));

        assertNotNull(userDto);
        assertEquals(userDto.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());

        verify(utils, Mockito.times(2)).generateAddressId(anyInt());


        assertEquals(userDto.getAddresses().size(), userEntity.getAddresses().size());
    }

    // test helper-method
    private List<AddressDto> getAddressesDto() {
        AddressDto firstAddress = new AddressDto();
        firstAddress.setCity("Gdynia");
        firstAddress.setCountry("Poland");
        firstAddress.setZipCode("11-234");
        firstAddress.setStreetName("Some street name 1/1");

        AddressDto secondAddress = new AddressDto();
        secondAddress.setCity("Gdańsk");
        secondAddress.setCountry("Poland");
        secondAddress.setZipCode("44-321");
        secondAddress.setStreetName("Some street name 2/2");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(firstAddress);
        addresses.add(secondAddress);

        return addresses;
    }

    // test helper-method
    private List<AddressEntity> getAddressesEntities() {

        List<AddressDto> addresses = getAddressesDto();

        return addresses.stream().map(addressDto -> modelMapper.map(addressDto, AddressEntity.class)).collect(Collectors.toList());
    }

    @Test
    void getUserByEmailSuccessful() throws UserServiceException {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserEntity user = userService.getUserByEmail(anyString());

        assertEquals("Anny", user.getFirstName());
        assertNotNull(user);
        verify(userRepository, times(1)).findByEmail(anyString());

    }

    @Test
    void getUserByEmailFailsAndReturnsUserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        UserEntity user = userRepository.findByEmail(anyString());

        assertThrows(UserServiceException.class, () -> {

            userService.getUserByUserId(anyString());
        });
        assertNull(user);
        verify(userRepository, times(1)).findByEmail(anyString());

    }

    @Test
    void getUserByUserIdSuccesfull() throws UserServiceException {
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        UserDto user = userService.getUserByUserId(anyString());

        assertNotNull(user);
        assertEquals("Anny", user.getFirstName());
        assertEquals(userId, user.getUserId());
        verify(userRepository, times(1)).findByUserId(anyString());
    }

    @Test
    void updateUser() throws UserServiceException {
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);

        UserDto updatedDto = userDto;
        updatedDto.setUserId("uPdtdUsr11");
        updatedDto.setFirstName("Newanny");

        UserEntity entityUpdated = userEntity;
        entityUpdated.setFirstName(updatedDto.getFirstName());
        entityUpdated.setUserId(updatedDto.getUserId());

        when(userRepository.save(entityUpdated)).thenReturn(entityUpdated);

        UserDto savedDto = new UserDto();
        savedDto.setFirstName(entityUpdated.getFirstName());
        savedDto.setLastName(entityUpdated.getLastName());
        savedDto.setUserId(entityUpdated.getUserId());
        savedDto.setEmail(entityUpdated.getEmail());

        when(modelMapper.map(entityUpdated, UserDto.class)).thenReturn(savedDto);

        UserDto userDto = userService.updateUser(userEntity.getEmail(), savedDto);

        assertEquals(savedDto.getFirstName(), entityUpdated.getFirstName());
        assertNotNull(entityUpdated);
        assertNotNull(userDto);
        verify(userRepository, times(1)).findByUserId(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));


    }

    @Test
    void deleteUser() throws UserServiceException {
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        UserDto userDto = userService.getUserByUserId(anyString());

        userService.deleteUser(anyString());


        assertNotNull(userDto);
        verify(userRepository, times(1)).delete(any(UserEntity.class));
    }

    @Test
    void deleteUserThrowsUserServiceException() throws UserServiceException {
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        UserEntity user = userRepository.findByUserId(anyString());
        assertThrows(UserServiceException.class, () -> {

            userService.getUserByUserId(anyString());
        });
        assertNull(user);

    }
    
    @Test
    void getUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDetails user = userService.loadUserByUsername(anyString());

        assertNotNull(user);
        assertEquals(user.getUsername(), userEntity.getEmail());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getUserByEmailThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, () -> {

            userService.loadUserByUsername(anyString());
        });

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getUserByUserId() throws UserServiceException {
        when(userRepository.findByUserId(userId)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);
        UserDto user = userService.getUserByUserId(userId);

        assertNotNull(user);
        verify(userRepository, times(1)).findByUserId(userId);
    }
}