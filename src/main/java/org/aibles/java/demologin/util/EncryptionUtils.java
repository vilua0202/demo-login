package org.aibles.java.demologin.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {
    private final PasswordEncoder passwordEncoder;

    public EncryptionUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public boolean match(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}
