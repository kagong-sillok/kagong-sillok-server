package org.prography.kagongsillok.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.auth.application.AuthService;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.common.resolver.dto.LoginMemberDto;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @Test
    void 멤버를_조회한다() {
        final Long memberId = localLoginMemberId();
        final LoginMemberDto loginMemberDto = new LoginMemberDto(memberId, Role.MEMBER);
        final MemberDto memberDto = memberService.getMember(loginMemberDto);

        assertAll(
                () -> assertThat(memberDto.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(memberDto.getNickname()).isEqualTo("닉네임"),
                () -> assertThat(memberDto.getRole()).isSameAs(Role.MEMBER.name()),
                () -> assertThat(memberDto.getLoginCount()).isEqualTo(1)
        );
    }

    private Long localLoginMemberId() {
        final LocalJoinCommand command = LocalJoinCommand.builder()
                .loginId("loginId")
                .password("password")
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER.name())
                .build();

        return authService.localJoin(command).getId();
    }
}
