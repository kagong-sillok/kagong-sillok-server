package org.prography.kagongsillok.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.auth.application.AuthService;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.auth.infrastructure.JwtAuthTokenProvider;
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

    @Autowired
    private JwtAuthTokenProvider jwtAuthTokenProvider;

    @Test
    void 멤버를_조회한다() {
        final Long memberId = localLoginMemberId();
        final String accessToken = jwtAuthTokenProvider.createAccessToken(memberId, Role.MEMBER,
                ZonedDateTime.of(
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59)),
                        ZoneId.of("Asia/Seoul")
                )
        );

        final MemberDto memberDto = memberService.getMember(accessToken);

        assertAll(
                () -> assertThat(memberDto.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(memberDto.getNickname()).isEqualTo("닉네임"),
                () -> assertThat(memberDto.getRole()).isSameAs(Role.MEMBER.name()),
                () -> assertThat(memberDto.getLoginCount()).isEqualTo(1),
                () -> assertThat(memberDto.getTotalStudyTime()).isEqualTo(0)
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
