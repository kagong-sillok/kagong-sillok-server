package org.prography.kagongsillok.auth.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.Role;

class LocalAccountTest {

    @Test
    void 로컬_계정을_생성한다() {
        final Member member = Member.builder()
                .nickname("테스트")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build();

        final LocalAccount localAccount = new LocalAccount("loginId", "password", member);

        assertAll(
                () -> assertThat(localAccount.getLoginId()).isEqualTo("loginId"),
                () -> assertThat(localAccount.getEncryptedPassword()).isEqualTo("password"),
                () -> assertThat(localAccount.getMember().getNickname()).isEqualTo("테스트"),
                () -> assertThat(localAccount.getMember().getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(localAccount.getMember().getRole()).isSameAs(Role.MEMBER),
                () -> assertThat(localAccount.getIsDeleted()).isFalse()
        );
    }

    @Test
    void 로컬_계정을_삭제한다() {
        final Member member = Member.builder()
                .nickname("테스트")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build();
        final LocalAccount localAccount = new LocalAccount("loginId", "password", member);

        localAccount.delete();

        assertThat(localAccount.getIsDeleted()).isTrue();
    }
}
