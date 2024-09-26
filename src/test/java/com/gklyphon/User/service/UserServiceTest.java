
package com.gklyphon.User.service;

import com.gklyphon.User.exception.custom.UserNotFoundException;
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
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserService}, verifying the service's ability
 * to manage user data, including retrieval, saving, enabling, and
 * disabling users.
 *
 * <p>Created on: 2024-09-24</p>
 * @author JFCiscoHuerta
 */
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

    /**
     * Initializes test data before each test method.
     *
     * <p>This method sets up instances of User, UserDTO, and UserRequestDTO
     * needed for the tests. It ensures a clean state for each test, avoiding
     * shared state issues.</p>
     */
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


    /**
     * Tests the findById method in the user service.
     *
     * <p>This test verifies that when the findById method is called
     * with a valid user ID, it returns the corresponding UserDTO.
     * It also checks that the repository and mapper methods are called correctly.</p>
     */
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

    /**
     * Tests the findAll method in the user service.
     *
     * <p>This test verifies that when the findAll method is called,
     * it returns a list of UserDTOs. It also checks that the repository
     * and mapper methods are invoked as expected.</p>
     */
    @Test
    void shouldReturnUser_whenFindByUsernameCalled() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        User userMocked = userService.findByUsername("user");
        verify(userRepository).findByUsername(anyString());
        assertEquals(user.getUsername(), userMocked.getUsername());
    }

    /**
     * Tests the findByUsername method in the user service.
     *
     * <p>This test verifies that when the findByUsername method is called
     * with a valid username, it returns the corresponding User object.
     * It also ensures that the repository method is called.</p>
     */
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

    /**
     * Tests the save method in the user service.
     *
     * <p>This test verifies that when the save method is called with a
     * UserRequestDTO, it saves the user and returns the corresponding
     * UserDTO. It also checks that the appropriate repository and mapper
     * methods are called.</p>
     */
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

    /**
     * Tests the disableUser method in the user service.
     *
     * <p>This test verifies that when the disableUser method is called
     * with a valid user ID, it disables the user and returns the corresponding
     * UserDTO. It also ensures that the repository and mapper methods
     * are invoked correctly.</p>
     */
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

    /**
     * Tests the enableUser method in the user service.
     *
     * <p>This test verifies that when the enableUser method is called
     * with a valid user ID, it enables the user and returns the corresponding
     * UserDTO. It also checks that the repository and mapper methods are called correctly.</p>
     */
    @Test
    void shouldThrowUserNotFoundException_whenUserNotFound() {
        doThrow(UserNotFoundException.class).when(userRepository).findById(100L);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(100L);
        });
    }
}
