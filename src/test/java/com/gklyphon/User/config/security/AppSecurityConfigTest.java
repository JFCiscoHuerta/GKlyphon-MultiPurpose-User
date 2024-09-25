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

import com.gklyphon.User.UserApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link AppSecurityConfig}
 *
 * <p>Created on: 2024-09-24</p>
 * @author JFCiscoHuerta
 */
@SpringBootTest(classes = UserApplication.class)
@TestPropertySource(properties = "security.password.pbkdf2-secret=secret")
class AppSecurityConfigTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String TEST_PASSWORD = "Raw_password%_-¿";

    /**
     * Tests that the {@link PasswordEncoder#encode(CharSequence)} method correctly encodes a password.
     * This test validates that:
     * <ul>
     *     <li>The encoded password is not null.</li>
     *     <li>The original password matches the encoded password when verified.</li>
     * </ul>
     *
     * @see PasswordEncoder#matches(CharSequence, String)
     */
    @Test
    void shouldEncodePhrase_whenEncodeCalled() {
        String phrasePbkdf2Encoded = passwordEncoder.encode(TEST_PASSWORD);
        assertNotNull(phrasePbkdf2Encoded, "Encoded password should not be null");
        assertTrue(passwordEncoder.matches(TEST_PASSWORD, phrasePbkdf2Encoded), "The original password should match the encoded password");
    }

    /**
     * Tests that encoding the same password multiple times produces different hashes.
     * This test ensures the security of the encoding process by verifying that:
     * <ul>
     *     <li>Each encoded password is unique, even for the same input.</li>
     * </ul>
     *
     * This behavior is critical for preventing rainbow table attacks and ensuring the security of stored passwords.
     */
    @Test
    void shouldProduceDifferentHashersForSameInput() {
        String encodedPasswd1 = passwordEncoder.encode(TEST_PASSWORD);
        String encodedPasswd2 = passwordEncoder.encode(TEST_PASSWORD);
        assertNotEquals(encodedPasswd1, encodedPasswd2, "Encoded passwords should not be equal for the same input");
    }
}