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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppSecurityConfig {

    private final AppSecurityData appSecurityData;
    private final String pbkdf2Secret;

    public AppSecurityConfig(AppSecurityData appSecurityData) {
        this.appSecurityData = appSecurityData;
        this.pbkdf2Secret = appSecurityData.getPbkdf2Secret();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncoding = "pbkdf2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encodersBuilding(encoders, idForEncoding);
        return new DelegatingPasswordEncoder(idForEncoding, encoders);
    }

    private void encodersBuilding(Map<String, PasswordEncoder> encoders, String idForEncoding) {
        encoders.put(idForEncoding, new Pbkdf2PasswordEncoder(pbkdf2Secret, 16, 310_000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));
        encoders.put("bcrypt", new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12));
        encoders.put("scrypt", new SCryptPasswordEncoder(16384, 8, 1, 32, 64));
    }

}
