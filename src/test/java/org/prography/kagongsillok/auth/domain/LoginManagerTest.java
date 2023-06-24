package org.prography.kagongsillok.auth.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.MockTestConfig;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberInfo;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(MockTestConfig.class)
class LoginManagerTest {

    @Autowired
    private LoginManager loginManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Test
    void 로그인하면_액세스_토큰과_리프레시_토큰을_반환한다() {
        final Member member = new Member("닉네임", "test@test.com", Role.MEMBER);
        final Member savedMember = memberRepository.save(member);

        final LoginResultDto loginResultDto = loginManager.loginMember(savedMember);

        final LoginMemberInfo loginMember = authTokenProvider.getLoginMemberByAccessToken(loginResultDto.getAccessToken());
        final Long memberIdRefreshToken = authTokenProvider.getMemberIdByRefreshToken(loginResultDto.getRefreshToken());
        assertAll(
                () -> assertThat(loginMember.getRole()).isSameAs(Role.MEMBER),
                () -> assertThat(loginMember.getMemberId()).isEqualTo(member.getId()),
                () -> assertThat(memberIdRefreshToken).isEqualTo(member.getId())
        );
    }

    @Test
    void 리프레시_토큰을_사용하여_토큰을_갱신한다() throws InterruptedException {
        final Member member = new Member("닉네임", "test@test.com", Role.MEMBER);
        final Member savedMember = memberRepository.save(member);
        final LoginResultDto loginResultDto = loginManager.loginMember(savedMember);
        Thread.sleep(1000);

        final LoginResultDto refreshResultDto = loginManager.refresh(loginResultDto.getRefreshToken());

        final LoginMemberInfo loginMember = authTokenProvider.getLoginMemberByAccessToken(refreshResultDto.getAccessToken());
        final Long memberIdRefreshToken
                = authTokenProvider.getMemberIdByRefreshToken(refreshResultDto.getRefreshToken());
        assertAll(
                () -> assertThat(loginMember.getRole()).isSameAs(Role.MEMBER),
                () -> assertThat(loginMember.getMemberId()).isEqualTo(member.getId()),
                () -> assertThat(memberIdRefreshToken).isEqualTo(member.getId()),
                () -> assertThat(refreshResultDto.getRefreshToken()).isNotEqualTo(loginResultDto.getRefreshToken()),
                () -> assertThat(refreshResultDto.getAccessToken()).isNotEqualTo(loginResultDto.getAccessToken())
        );
    }
}
