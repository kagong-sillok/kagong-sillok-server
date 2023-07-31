package org.prography.kagongsillok.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.auth.domain.dto.AccessTokenCreateInfo;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberInfo;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccessTokenManagerTest {

    @Autowired
    private AccessTokenManager accessTokenManager;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 액세스_토큰을_생성한다() {
        final Member member = Member.builder()
                .nickname("testMember")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build();
        final Member savedMember = memberRepository.save(member);

        final AccessTokenCreateInfo accessTokenCreateInfo = accessTokenManager.create(member);

        final LoginMemberInfo loginMember = authTokenProvider.getLoginMemberByAccessToken(
                accessTokenCreateInfo.getAccessToken());
        assertAll(
                () -> assertThat(loginMember.getMemberId()).isEqualTo(savedMember.getId()),
                () -> assertThat(loginMember.getRole()).isEqualTo(savedMember.getRole())
        );
    }
}
