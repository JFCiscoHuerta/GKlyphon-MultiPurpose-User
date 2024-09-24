package com.gklyphon.User.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
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
