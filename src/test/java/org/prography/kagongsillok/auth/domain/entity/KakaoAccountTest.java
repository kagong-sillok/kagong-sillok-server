package org.prography.kagongsillok.auth.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.Role;

class KakaoAccountTest {

    @Test
    void 카카오_계정을_생성한다() {
        final Member member = new Member("테스트", "test@test.com", Role.MEMBER);

        final KakaoAccount kakaoAccount = new KakaoAccount(999_999L, member);

        assertAll(
                () -> assertThat(kakaoAccount.getKakaoId()).isEqualTo(999_999L),
                () -> assertThat(kakaoAccount.getMember().getNickname()).isEqualTo("테스트"),
                () -> assertThat(kakaoAccount.getMember().getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(kakaoAccount.getMember().getRole()).isSameAs(Role.MEMBER),
                () -> assertThat(kakaoAccount.getIsDeleted()).isFalse()
        );
    }

    @Test
    void 카카오_계정을_삭제한다() {
        final Member member = new Member("테스트", "test@test.com", Role.MEMBER);
        final KakaoAccount kakaoAccount = new KakaoAccount(999_999L, member);

        kakaoAccount.delete();

        assertThat(kakaoAccount.getIsDeleted()).isTrue();
    }
}
