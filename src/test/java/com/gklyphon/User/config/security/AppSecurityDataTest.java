/**
 * MIT License
 *
 * Copyright (c) 2024 JFCiscoHuerta
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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