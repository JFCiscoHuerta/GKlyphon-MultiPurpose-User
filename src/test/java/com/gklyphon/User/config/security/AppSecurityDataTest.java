package com.gklyphon.User.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppSecurityDataTest {

    @Autowired
    AppSecurityData appSecurityData;

    String secret;

    @BeforeEach
    void setUp() {
        secret = appSecurityData.getPbkdf2Secret();
    }

    @Test
    void shouldNotRecoverNullSecretFromEnvironmentVariables() {
        assertNotNull(secret);
        assertTrue(secret.length() > 30);
    }
}