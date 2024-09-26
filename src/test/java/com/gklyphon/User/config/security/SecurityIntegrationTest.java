
package com.gklyphon.User.config.security;

import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.model.entity.Country;
import com.gklyphon.User.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the {@link AppSecurityConfig} class.
 *
 * <p>This class verifies the behavior of AppSecurityData, ensuring the endpoints
 * protection against unauthorized request attempts.</p>
 *
 * <p>Created on: 2024-09-25</p>
 * @author JFCiscoHuerta
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    String baseApiUrl = "/v1/user" ;
    UserDTO userDTO;
    List<UserDTO> userDTOS;

    @BeforeEach
    void setUp() {

        userDTO = new UserDTO(1L, "user@gmail.com", "user", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", new Country(1L, "Mexico", "MX"));

        userDTOS = List.of(new UserDTO(1L, "user@gmail.com", "user", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", new Country(1L, "Mexico", "MX")));
    }

    /**
     * Tests that non-authenticated users are denied access to the 'find all users' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldDenyAccessToFindAllUsersForNonAuthenticatedUser() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get(baseApiUrl + "/all-users")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isForbidden());
    }

    /**
     * Tests that users with the 'USER' role are denied access to the 'find all users' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldDenyAccessToFindAllUsersForNonAdminUser() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get(baseApiUrl + "/all-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    /**
     * Tests that users with the 'ADMIN' role are granted access to the 'find all users' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldAllowAccessToFindAllUsersForAdminUser() throws Exception {

        when(userService.findAll()).thenReturn(userDTOS);
        mockMvc.perform(MockMvcRequestBuilders.get(baseApiUrl + "/all-users")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        )
                .andExpect(status().isOk());
    }

    /**
     * Tests that non-authenticated users are denied access to the 'find user by ID' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldDenyAccessToFindUserByIdForNonAuthenticatedUsers() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get(baseApiUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    /**
     * Tests that users with the 'USER' role are granted access to the 'find user by ID' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldAllowAccessToFindUserByIdForNonAdminUser() throws Exception{

        when(userService.findById(anyLong())).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.get(baseApiUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    /**
     * Tests that users with the 'USER' role are granted access to the 'find user by ID' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldAllowAccessToFindUserByIdForAdminUser() throws Exception {

        when(userService.findById(anyLong())).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.get(baseApiUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    /**
     * Tests that non-authorized users are denied access to the 'toggle user' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldDenyAccessToToggleUserForNonAuthorizedUsers() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(baseApiUrl + "/toggle-user/1?enabled=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isForbidden());
    }

    /**
     * Tests that users with the 'USER' role are denied access to the 'toggle user' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldDenyAccessToToggleUserForNonAdminUser() throws Exception {

        when(userService.enableUser(anyLong())).thenReturn(userDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(baseApiUrl + "/toggle-user/1?enabled=true")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    /**
     * Tests that users with the 'ADMIN' role are granted access to the 'toggle user' endpoint.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldAllowAccessToToggleUserForAdminUser() throws Exception {

        when(userService.enableUser(anyLong())).thenReturn(userDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(baseApiUrl + "/toggle-user/1?enabled=true")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk());
    }

}
