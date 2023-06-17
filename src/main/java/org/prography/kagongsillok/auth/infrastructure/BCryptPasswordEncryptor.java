package org.prography.kagongsillok.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.domain.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordEncryptor implements PasswordEncryptor {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encrypt(final String planePassword) {
        return bCryptPasswordEncoder.encode(planePassword);
    }

    @Override
    public boolean matches(final String planePassword, final String encryptedPassword) {
        return bCryptPasswordEncoder.matches(planePassword, encryptedPassword);
    }
}
