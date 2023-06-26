package org.prography.kagongsillok.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class BCryptPasswordEncryptorTest {

    private final BCryptPasswordEncryptor bCryptPasswordEncryptor
            = new BCryptPasswordEncryptor(new BCryptPasswordEncoder());

    @Test
    void 비밀번호를_암호화하고_결과를_비교한다() {
        final String planePassword = "testPassword123@";

        final String encryptedPassword = bCryptPasswordEncryptor.encrypt(planePassword);

        assertThat(bCryptPasswordEncryptor.matches(planePassword, encryptedPassword)).isTrue();
    }

    @Test
    void 안맞는_비밀번호를_비교하면_false_가_반환된다() {
        final String planePassword = "testPassword123@";

        final String encryptedPassword = bCryptPasswordEncryptor.encrypt(planePassword);

        assertThat(bCryptPasswordEncryptor.matches("notMatchPassword", encryptedPassword)).isFalse();
    }
}