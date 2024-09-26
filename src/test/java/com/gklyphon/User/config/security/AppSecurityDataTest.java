
package com.gklyphon.User.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link AppSecurityData} class.
 *
 * <p>This class verifies the behavior of AppSecurityData, ensuring the loading
 * from the environment variables.</p>
 *
 * <p>Created on: 2024-09-25</p>
 * @author JFCiscoHuerta
 */
@SpringBootTest
class AppSecurityDataTest {

    @Autowired
    AppSecurityData appSecurityData;

    String secret;

    /**
     * Sets up the test environment before each test case.
     * This method retrieves the PBKDF2 secret from {@link AppSecurityData}
     * to be used in the subsequent tests.
     */
    @BeforeEach
    void setUp() {
        secret = appSecurityData.getPbkdf2Secret();
    }

    /**
     * Tests that the PBKDF2 secret is not null and has a sufficient length.
     * This test verifies that the secret is correctly recovered from the
     * environment variables or the properties file, ensuring its validity for
     * cryptographic purposes.
     *
     * <p>Asserts that:</p>
     * <ul>
     *     <li>The secret is not null.</li>
     *     <li>The length of the secret is greater than 30 characters.</li>
     * </ul>
     */
    @Test
    void shouldNotRecoverNullSecretFromEnvironmentVariables() {
        assertNotNull(secret, "PBKDF2 secret should not be null");
        assertTrue(secret.length() > 30, "PBKDF2 secret length should be greater than 30 characters");
    }
}