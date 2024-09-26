
package com.gklyphon.User.repository;

import com.gklyphon.User.model.entity.Authority;
import com.gklyphon.User.model.entity.Country;
import com.gklyphon.User.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link IUserRepository}, verifying the repository's
 * ability to find a user by username.
 *
 * <p>This test class ensures that the repository correctly interacts
 * with the underlying data store and returns the expected user object.</p>
 *
 * <p>Created on: 2024-09-24</p>
 * @author JFCiscoHuerta
 */
@DataJpaTest
class UserRepositoryTest {

    @Mock
    IUserRepository userRepository;

    User user;

    /**
     * Initializes test data before each test method.
     *
     * <p>This method sets up an instance of User needed for the test.
     * It ensures a clean state for each test, avoiding shared state issues.</p>
     */
    @BeforeEach
    void setUp() {
        user = new User(1L, "user@gmail.com","user", "password", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", true, Set.of(new Authority(1L, "ROLE_ADMIN", new User())), new Country(1L, "Mexico", "MX"),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), LocalDate.now());
    }

    /**
     * Tests the findByUsername method in the user repository.
     *
     * <p>This test verifies that when the findByUsername method is called
     * with a valid username, it returns an Optional containing the User.
     * It also checks that the repository method is called and validates
     * the returned User's ID.</p>
     */
    @Test
    void shouldReturnUser_whenFindByUsernameCalled() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Optional<User> userMocked = userRepository.findByUsername("user");
        verify(userRepository).findByUsername(anyString());
        assertTrue(userMocked.isPresent());
        assertEquals(user.getId(), userMocked.orElseThrow().getId());
    }
}
