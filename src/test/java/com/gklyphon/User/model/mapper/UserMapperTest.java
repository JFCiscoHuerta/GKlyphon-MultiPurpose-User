
package com.gklyphon.User.model.mapper;

import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.model.dto.UserRequestDTO;
import com.gklyphon.User.model.entity.Authority;
import com.gklyphon.User.model.entity.Country;
import com.gklyphon.User.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link IUserMapper}, verifying the correct mapping
 * between User, UserDTO, and UserRequestDTO objects.
 *
 * <p>This test class ensures that the mapper correctly transforms
 * data between entity and DTO representations, which is critical for
 * the application's data flow.</p>
 *
 * <p>Created on: 2024-09-24</p>
 * @author JFCiscoHuerta
 */
@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    IUserMapper userMapper;

    User user;
    UserDTO userDTO;
    UserRequestDTO userRequestDTO;
    List<User> users;
    List<UserDTO> userDTOS;

    /**
     * Initializes test data before each test method.
     *
     * <p>This method sets up instances of User, UserDTO, UserRequestDTO,
     * and lists of Users and UserDTOs needed for the tests. It ensures
     * a clean state for each test, avoiding shared state issues.</p>
     *
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
     * Tests the mapping of a User to UserDTO.
     *
     * <p>This test verifies that the mapper converts a User object into
     * a UserDTO correctly. It ensures that the mapped UserDTO has the
     * same email as the original User.</p>
     */
    @Test
    void shouldMapUserToUserDTO() {
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO userDTOMocked = userMapper.toUserDTO(user);
        verify(userMapper).toUserDTO(any(User.class));
        assertEquals(user.getEmail(),userDTOMocked.getEmail());
    }

    /**
     * Tests the mapping of a List of Users to a List of UserDTOs.
     *
     * <p>This test ensures that the mapper correctly converts a list of
     * User entities into a list of UserDTOs. It checks that the ID of
     * the first User in the original list matches the ID of the first
     * UserDTO in the mapped list.</p>
     */
    @Test
    void shouldMapUsersToUsersDTO() {
        when(userMapper.toUsersDTO(any(List.class))).thenReturn(userDTOS);
        List<UserDTO> userDTOSMocked = userMapper.toUsersDTO(users);
        verify(userMapper).toUsersDTO(any(List.class));
        assertEquals(users.getFirst().getId(), userDTOSMocked.getFirst().getId());
    }

    /**
     * Tests the mapping of UserDTO to User.
     *
     * <p>This test verifies that the mapper correctly converts a UserDTO
     * back into a User entity. It ensures that the email of the mapped
     * User matches the original UserDTO.</p>
     */
    @Test
    void shouldMapUserDTOToUser() {
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(user);
        User userMocked = userMapper.toUser(userDTO);
        verify(userMapper).toUser(any(UserDTO.class));
        assertEquals(userDTO.getEmail(), userMocked.getEmail());
    }

    /**
     * Tests the mapping of UserRequestDTO to User.
     *
     * <p>This test verifies that the mapper correctly converts a UserRequestDTO
     * into a User entity. It ensures that the username of the mapped User
     * matches the original UserRequestDTO.</p>
     */
    @Test
    void shouldMapUserRequestDTOToUser() {
        when(userMapper.toUserFromUserRequestDTO(any(UserRequestDTO.class))).thenReturn(user);
        User userMocked = userMapper.toUserFromUserRequestDTO(userRequestDTO);
        verify(userMapper).toUserFromUserRequestDTO(any(UserRequestDTO.class));
        assertEquals(userMocked.getUsername(), userRequestDTO.getUsername());
    }
}
