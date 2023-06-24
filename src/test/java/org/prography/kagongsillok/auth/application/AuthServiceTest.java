package org.prography.kagongsillok.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.MockTestConfig;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.AuthTokenProvider;
import org.prography.kagongsillok.auth.domain.LocalAccountRepository;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberInfo;
import org.prography.kagongsillok.auth.domain.entity.LocalAccount;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(MockTestConfig.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private LocalAccountRepository localAccountRepository;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Test
    void 로컬_회원_가입한다() {
        final LocalJoinCommand command = LocalJoinCommand.builder()
                .loginId("loginId")
                .password("password")
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER.name())
                .build();

        final MemberDto memberDto = authService.localJoin(command);

        final LocalAccount localAccount = localAccountRepository.findByLoginId("loginId")
                .orElseThrow();
        assertAll(
                () -> assertThat(memberDto.getId()).isNotNull(),
                () -> assertThat(memberDto.getNickname()).isEqualTo("닉네임"),
                () -> assertThat(memberDto.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(memberDto.getRole()).isEqualTo(Role.MEMBER.name()),
                () -> assertThat(localAccount.getLoginId()).isEqualTo("loginId"),
                () -> assertThat(localAccount.getMember().getId()).isEqualTo(memberDto.getId())
        );
    }

    @Test
    void 로컬_회원_로그인한다() {
        final LocalJoinCommand command = LocalJoinCommand.builder()
                .loginId("loginId")
                .password("password")
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER.name())
                .build();
        final MemberDto memberDto = authService.localJoin(command);

        final LoginResultDto loginResultDto = authService.localLogin("loginId", "password");

        final LoginMemberInfo loginMemberInfo
                = authTokenProvider.getLoginMemberByAccessToken(loginResultDto.getAccessToken());
        final Long memberIdByRefreshToken
                = authTokenProvider.getMemberIdByRefreshToken(loginResultDto.getRefreshToken());
        assertAll(
                () -> assertThat(loginMemberInfo.getMemberId()).isEqualTo(memberDto.getId()),
                () -> assertThat(loginMemberInfo.getRole().name()).isEqualTo(memberDto.getRole()),
                () -> assertThat(memberIdByRefreshToken).isEqualTo(memberDto.getId())
        );
    }

    @Test
    void 토큰을_갱신한다() {
        final LocalJoinCommand command = LocalJoinCommand.builder()
                .loginId("loginId")
                .password("password")
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER.name())
                .build();
        final MemberDto memberDto = authService.localJoin(command);
        final LoginResultDto loginResultDto = authService.localLogin("loginId", "password");

        final LoginResultDto refreshResult = authService.refresh(loginResultDto.getRefreshToken());

        final LoginMemberInfo loginMemberInfo
                = authTokenProvider.getLoginMemberByAccessToken(refreshResult.getAccessToken());
        final Long memberIdByRefreshToken
                = authTokenProvider.getMemberIdByRefreshToken(refreshResult.getRefreshToken());
        assertAll(
                () -> assertThat(loginMemberInfo.getMemberId()).isEqualTo(memberDto.getId()),
                () -> assertThat(loginMemberInfo.getRole().name()).isEqualTo(memberDto.getRole()),
                () -> assertThat(memberIdByRefreshToken).isEqualTo(memberDto.getId())
        );
    }
}
