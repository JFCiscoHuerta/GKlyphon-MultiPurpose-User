package com.gklyphon.User.service;

import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.model.dto.UserRequestDTO;
import com.gklyphon.User.model.entity.Authority;
import com.gklyphon.User.model.entity.Country;
import com.gklyphon.User.model.entity.User;
import com.gklyphon.User.model.mapper.IUserMapper;
import com.gklyphon.User.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.AtLeast;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    IUserMapper userMapper;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    User user;
    UserDTO userDTO;
    UserRequestDTO userRequestDTO;
    List<User> users;
    List<UserDTO> userDTOS;

    @BeforeEach
    void setUp() {
        user = new User(1L, "user@gmail.com","user", "password", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", true, Set.of(new Authority(1L, "ROLE_ADMIN", new User())), new Country(1L, "Mexico", "MX"),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), LocalDate.now());

        userDTO = new UserDTO(1L, "user@gmail.com", "user", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", new Country(1L, "Mexico", "MX"));

        userRequestDTO = new UserRequestDTO(
                "user@gmail.com","user", "password", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", new Country(1L, "Mexico", "MX")
        );

        users = List.of(user = new User(1L, "user@gmail.com","user", "password", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", true, Set.of(new Authority(1L, "ROLE_ADMIN", new User())), new Country(1L, "Mexico", "MX"),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), LocalDate.now()));

        userDTOS = List.of(userDTO = new UserDTO(1L, "user@gmail.com", "user", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", new Country(1L, "Mexico", "MX")));
    }

    @Test
    void shouldReturnUser_whenFindByIdCalled() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);
        UserDTO userDTOMocked = userService.findById(1L);
        verify(userRepository).findById(anyLong());
        verify(userMapper).toUserDTO(any(User.class));
        assertEquals(user.getId(), userDTOMocked.getId());
    }

    @Test
    void shouldReturnUsers_whenFindAllCalled() {
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUsersDTO(anyList())).thenReturn(userDTOS);
        List<UserDTO> userDTOSMocked = userService.findAll();
        verify(userRepository).findAll();
        verify(userMapper).toUsersDTO(anyList());
        assertNotNull(userDTOSMocked);
        assertEquals(users.getFirst().getId(), userDTOSMocked.getFirst().getId());
    }

    @Test
    void shouldReturnUser_whenFindByUsernameCalled() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        User userMocked = userService.findByUsername("user");
        verify(userRepository).findByUsername(anyString());
        assertEquals(user.getUsername(), userMocked.getUsername());
    }

    @Test
    void shouldSaveUser_whenSaveCalled() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserFromUserRequestDTO(any(UserRequestDTO.class))).thenReturn(user);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);
        UserDTO userDTOMocked = userService.save(userRequestDTO);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toUserFromUserRequestDTO(any(UserRequestDTO.class));
        verify(userMapper).toUserDTO(any(User.class));
        assertEquals(userDTOMocked.getEmail(), userRequestDTO.getEmail(), "El email del usuario deberia ser el mismo");
    }

    @Test
    void shouldDisableUser_whenDisableUserByIdCalled() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);
        UserDTO userDTOMocked = userService.disableUser(1L);
        User userMocked = userRepository.findById(1L).orElseThrow();
        verify(userRepository, atLeast(2)).findById(anyLong());
        verify(userMapper).toUserDTO(any(User.class));
        assertFalse(userMocked.getStatus(), "El usuario deberia estar deshabilitado");
    }

    @Test
    void shouldEnableUser_whenEnableUserByIdCalled() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);
        UserDTO userDTOMocked = userService.enableUser(1L);
        User userMocked = userRepository.findById(1L).orElseThrow();
        verify(userRepository, atLeast(2)).findById(anyLong());
        verify(userMapper).toUserDTO(any(User.class));
        assertTrue(userMocked.getStatus(), "El usuario deberia esta habilitado");
    }
}
