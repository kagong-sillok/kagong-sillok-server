package org.prography.kagongsillok.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 회원들_생성한다() {
        final Member member = Member.builder()
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build();

        assertAll(
                () -> assertThat(member.getNickname()).isEqualTo("닉네임"),
                () -> assertThat(member.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(member.getRole()).isSameAs(Role.MEMBER),
                () -> assertThat(member.getProfileImageUrl()).isNull(),
                () -> assertThat(member.getLoginHistory().getLatestLoginDate()).isEqualTo(LocalDate.now()),
                () -> assertThat(member.getLoginCount()).isEqualTo(1),
                () -> assertThat(member.getIsDeleted()).isFalse()
        );
    }

    @Test
    void 회원을_삭제한다() {
        final Member member = Member.builder()
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build();

        member.delete();

        assertThat(member.getIsDeleted()).isTrue();
    }
}
