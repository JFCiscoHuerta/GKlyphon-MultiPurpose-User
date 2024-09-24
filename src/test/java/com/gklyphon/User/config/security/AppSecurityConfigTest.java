package com.gklyphon.User.config.security;

import com.gklyphon.User.UserApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserApplication.class)
@TestPropertySource(properties = "security.password.pbkdf2-secret=secret")
class AppSecurityConfigTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void shouldEncodePhrase_whenEncodeCalled() {
        String phrase = "Raw_password%_-¿";
        String phrasePbkdf2Encoded = passwordEncoder.encode(phrase);
        assertNotNull(phrasePbkdf2Encoded);
        assertTrue(passwordEncoder.matches(phrase, phrasePbkdf2Encoded));
    }



}