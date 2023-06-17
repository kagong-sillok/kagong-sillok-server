package org.prography.kagongsillok.auth.domain;

public interface PasswordEncryptor {

    String encrypt(String planePassword);

    boolean matches(String planePassword, String encryptedPassword);
}
