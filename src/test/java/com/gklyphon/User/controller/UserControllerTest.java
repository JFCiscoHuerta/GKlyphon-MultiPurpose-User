
package com.gklyphon.User.controller;

import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.model.dto.UserRequestDTO;
import com.gklyphon.User.model.entity.Authority;
import com.gklyphon.User.model.entity.Country;
import com.gklyphon.User.model.entity.User;
import com.gklyphon.User.model.mapper.IUserMapper;
import com.gklyphon.User.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link UserController}, verifying the correct handling of
 * user-related endpoints.
 *
 * <p>These tests mock the dependencies of the controller to isolate its
 * behavior and ensure it interacts correctly with the service layer and
 * returns appropriate responses.</p>
 *
 * <p>Created on: 2024-09-25</p>
 * @author JFCiscoHuerta
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    IUserMapper userMapper;
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    User user;
    UserDTO userDTO;
    UserRequestDTO userRequestDTO;
    List<User> users;
    List<UserDTO> userDTOS;
    String baseApiUrl;

    /**
     * Initializes test data before each test method.
     *
     * <p>This method sets up a user entity, DTOs, and related data needed for the
     * tests. It ensures that each test starts with a fresh set of objects to avoid
     * shared state between tests.</p>
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
        this.baseApiUrl = "/v1/user";
    }

    /**
     * Tests the retrieval of a single user by its ID.
     *
     * <p>This test ensures that when the controller is called with a valid user ID,
     * it returns the corresponding {@link UserDTO}. It checks the status, content type,
     * and specific fields in the JSON response.</p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnUser_whenFindByIdCalled() throws Exception {
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);
        when(userService.findById(anyLong())).thenReturn(userDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.get(baseApiUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"));
    }

    /**
     * Tests the retrieval of all users.
     *
     * <p>This test ensures that when the controller is called to retrieve all users,
     * it returns a list of {@link UserDTO}. It checks the status, content type, and
     * fields of the first user in the list.</p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldReturnUsersDTOList_whenFindAllCalled() throws Exception {
        when(userMapper.toUsersDTO(anyList())).thenReturn(userDTOS);
        when(userService.findAll()).thenReturn(userDTOS);
        mockMvc.perform(
                MockMvcRequestBuilders.get(baseApiUrl + "/all-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user"));

    }

    /**
     * Tests toggling a user's status to enabled.
     *
     * <p>This test verifies that when the controller is called to enable a user,
     * the service method {@link UserService#enableUser(Long)} is called, and the
     * response status is OK.</p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldToggleUserStatusToTrue_whenEnableUserStatusCalled() throws Exception {
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);
        when(userService.enableUser(anyLong())).thenReturn(userDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post(baseApiUrl + "/toggle-user/1?enabled=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());
        verify(userService).enableUser(1L);
    }

}
