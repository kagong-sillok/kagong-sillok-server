package org.prography.kagongsillok.auth.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalLoginRequest {

    private String loginId;
    private String password;

    public LocalLoginRequest(final String loginId, final String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
