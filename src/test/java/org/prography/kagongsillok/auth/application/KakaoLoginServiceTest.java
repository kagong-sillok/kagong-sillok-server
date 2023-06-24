package org.prography.kagongsillok.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.MockTestConfig;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.AuthTokenProvider;
import org.prography.kagongsillok.auth.domain.KakaoAccountRepository;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberInfo;
import org.prography.kagongsillok.auth.domain.entity.KakaoAccount;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(MockTestConfig.class)
class KakaoLoginServiceTest {

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private KakaoAccountRepository kakaoAccountRepository;

    @Test
    void 가입했던_적이_없으면_카카오_로그인시_계정이_생성된다() {
        final LoginResultDto loginResultDto = kakaoLoginService.kakaoLogin("autorizationCode", "redirectUri");

        final LoginMemberInfo loginMemberInfo
                = authTokenProvider.getLoginMemberByAccessToken(loginResultDto.getAccessToken());
        final Long memberIdByRefreshToken
                = authTokenProvider.getMemberIdByRefreshToken(loginResultDto.getRefreshToken());
        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(99_999_999L)
                .orElseThrow();
        assertAll(
                () -> assertThat(loginMemberInfo.getMemberId()).isEqualTo(kakaoAccount.getMember().getId()),
                () -> assertThat(loginMemberInfo.getRole()).isEqualTo(Role.MEMBER),
                () -> assertThat(memberIdByRefreshToken).isEqualTo(kakaoAccount.getMember().getId())
        );
    }

    @Test
    void 가입했던_적이_있으면_카카오_로그인시_기존_계정을_반환한다() {
        kakaoLoginService.kakaoLogin("autorizationCode", "redirectUri");

        final LoginResultDto loginResultDto = kakaoLoginService.kakaoLogin("autorizationCode", "redirectUri");

        final LoginMemberInfo loginMemberInfo
                = authTokenProvider.getLoginMemberByAccessToken(loginResultDto.getAccessToken());
        final Long memberIdByRefreshToken
                = authTokenProvider.getMemberIdByRefreshToken(loginResultDto.getRefreshToken());
        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(99_999_999L)
                .orElseThrow();
        assertAll(
                () -> assertThat(loginMemberInfo.getMemberId()).isEqualTo(kakaoAccount.getMember().getId()),
                () -> assertThat(loginMemberInfo.getRole()).isEqualTo(Role.MEMBER),
                () -> assertThat(memberIdByRefreshToken).isEqualTo(kakaoAccount.getMember().getId())
        );
    }
}
