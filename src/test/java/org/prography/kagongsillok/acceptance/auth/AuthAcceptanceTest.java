package org.prography.kagongsillok.acceptance.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.기본_닉네임_이메일_가입_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.기본_아이디_비밀번호_가입_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.로그인_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.카카오_로그인_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.토큰_갱신_바디;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.auth.ui.dto.KakaoLoginRequest;
import org.prography.kagongsillok.auth.ui.dto.LocalJoinRequest;
import org.prography.kagongsillok.auth.ui.dto.LocalLoginRequest;
import org.prography.kagongsillok.auth.ui.dto.LoginRefreshRequest;
import org.prography.kagongsillok.auth.ui.dto.LoginResultResponse;
import org.prography.kagongsillok.member.domain.Role;
import org.prography.kagongsillok.member.ui.dto.MemberResponse;

public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String AUTH_API_BASE_URL_V1 = "/api/v1/auth";

    @Test
    void 로컬_회원_가입한다() {
        final var 로컬_가입_요청_바디 = 기본_아이디_비밀번호_가입_요청_바디("닉네임", "test@test.com");

        final var 생성된_회원_응답 = 로컬_가입_요청(로컬_가입_요청_바디);

        생성된_회원_검증(생성된_회원_응답);
    }

    @Test
    void 로컬_로그인한다() {
        final var 로컬_가입_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("loginId", "password");
        final var 생성된_회원_응답 = 로컬_가입_요청(로컬_가입_요청_바디);
        final var 로그인_요청_바디 = 로그인_요청_바디("loginId", "password");

        final var 로그인_응답 = 로그인_요청(로그인_요청_바디);

        로그인_응답_검증(로그인_응답);
    }

    @Test
    void 토큰을_갱신한다() {
        final var 로컬_가입_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("loginId", "password");
        final var 생성된_회원_응답 = 로컬_가입_요청(로컬_가입_요청_바디);
        final var 로그인_요청_바디 = 로그인_요청_바디("loginId", "password");
        final var 로그인_응답 = 로그인_요청(로그인_요청_바디);
        final var 토큰_갱신_바디 = 토큰_갱신_바디(로그인_응답);

        final var 토큰_갱신_응답 = 토큰_갱신_요청(토큰_갱신_바디);

        토큰_응답_검증(토큰_갱신_응답);
    }

    @Test
    void 카카오_로그인한다() {
        final var 카카오_로그인_요청_바디 = 카카오_로그인_요청_바디("authorizationCode", "redirectUri");

        final var 로그인_응답 = 카카오_로그인_요청(카카오_로그인_요청_바디);

        로그인_응답_검증(로그인_응답);
    }

    private LoginResultResponse 카카오_로그인_요청(final KakaoLoginRequest request) {
        return 응답_바디_추출(post(AUTH_API_BASE_URL_V1 + "/kakao/login", request), LoginResultResponse.class);
    }

    private LoginResultResponse 토큰_갱신_요청(final LoginRefreshRequest request) {
        return 응답_바디_추출(post(AUTH_API_BASE_URL_V1 + "/refresh", request), LoginResultResponse.class);
    }

    private void 토큰_응답_검증(final LoginResultResponse response) {
        assertAll(
                () -> assertThat(response.getAccessToken()).isNotNull(),
                () -> assertThat(response.getRefreshToken()).isNotNull(),
                () -> assertThat(response.getAccessTokenExpireDateTime()).isAfter(ZonedDateTime.now()),
                () -> assertThat(response.getRefreshTokenExpireDateTime()).isAfter(ZonedDateTime.now())
        );
    }

    private LoginResultResponse 로그인_요청(final LocalLoginRequest request) {
        return 응답_바디_추출(post(AUTH_API_BASE_URL_V1 + "/local/login", request), LoginResultResponse.class);
    }

    private void 로그인_응답_검증(final LoginResultResponse response) {
        assertAll(
                () -> assertThat(response.getAccessToken()).isNotNull(),
                () -> assertThat(response.getRefreshToken()).isNotNull(),
                () -> assertThat(response.getAccessTokenExpireDateTime()).isAfter(ZonedDateTime.now()),
                () -> assertThat(response.getRefreshTokenExpireDateTime()).isAfter(ZonedDateTime.now())
        );
    }

    private MemberResponse 로컬_가입_요청(final LocalJoinRequest request) {
        return 응답_바디_추출(post(AUTH_API_BASE_URL_V1 + "/local/join", request), MemberResponse.class);
    }

    private void 생성된_회원_검증(final MemberResponse memberResponse) {
        assertAll(
                () -> assertThat(memberResponse.getId()).isNotNull(),
                () -> assertThat(memberResponse.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(memberResponse.getNickname()).isEqualTo("닉네임"),
                () -> assertThat(memberResponse.getRole()).isEqualTo(Role.MEMBER.name())
        );
    }
}
