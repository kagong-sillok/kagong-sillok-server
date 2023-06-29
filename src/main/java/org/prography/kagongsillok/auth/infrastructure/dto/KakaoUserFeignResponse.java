package org.prography.kagongsillok.auth.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoUserFeignResponse {

    private Long id;
    private KakaoAccountFeignResponse kakaoAccount;

    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    public String getNickname() {
        return kakaoAccount.getProfile()
                .getNickname();
    }

    public String getProfileImageUrl() {
        return kakaoAccount.getProfile()
                .getProfileImageUrl();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(SnakeCaseStrategy.class)
    public static class KakaoAccountFeignResponse {

        private String email;
        private ProfileFeignResponse profile;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(SnakeCaseStrategy.class)
    public static class ProfileFeignResponse {

        private String nickname;
        private String profileImageUrl;
    }
}
