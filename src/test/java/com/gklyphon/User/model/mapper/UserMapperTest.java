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


@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    IUserMapper userMapper;

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
    void shouldMapUserToUserDTO() {
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO userDTOMocked = userMapper.toUserDTO(user);
        verify(userMapper).toUserDTO(any(User.class));
        assertEquals(user.getEmail(),userDTOMocked.getEmail());
    }

    @Test
    void shouldMapUsersToUsersDTO() {
        when(userMapper.toUsersDTO(any(List.class))).thenReturn(userDTOS);
        List<UserDTO> userDTOSMocked = userMapper.toUsersDTO(userDTOS);
        verify(userMapper).toUsersDTO(any(List.class));
        assertEquals(users.getFirst().getId(), userDTOSMocked.getFirst().getId());
    }

    @Test
    void shouldMapUserDTOToUser() {
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(user);
        User userMocked = userMapper.toUser(userDTO);
        verify(userMapper).toUser(any(UserDTO.class));
        assertEquals(userDTO.getEmail(), userMocked.getEmail());
    }

    @Test
    void shouldMapUserRequestDTOToUser() {
        when(userMapper.toUserFromUserRequestDTO(any(UserRequestDTO.class))).thenReturn(user);
        User userMocked = userMapper.toUserFromUserRequestDTO(userRequestDTO);
        verify(userMapper).toUserFromUserRequestDTO(any(UserRequestDTO.class));
        assertEquals(userMocked.getUsername(), userRequestDTO.getUsername());
    }
}
