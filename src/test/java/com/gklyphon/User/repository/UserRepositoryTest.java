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

@DataJpaTest
class UserRepositoryTest {

    @Mock
    IUserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "user@gmail.com","user", "password", "Javier", "Gonzalez",
                new Date(), "1234567890", "path", true, Set.of(new Authority(1L, "ROLE_ADMIN", new User())), new Country(1L, "Mexico", "MX"),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), LocalDate.now());
    }

    @Test
    void shouldReturnUser_whenFindByUsernameCalled() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Optional<User> userMocked = userRepository.findByUsername("user");
        verify(userRepository).findByUsername(anyString());
        assertTrue(userMocked.isPresent());
        assertEquals(user.getId(), userMocked.orElseThrow().getId());
    }
}
