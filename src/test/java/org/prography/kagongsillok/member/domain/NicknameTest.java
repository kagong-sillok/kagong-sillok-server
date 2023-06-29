package org.prography.kagongsillok.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.member.domain.exception.TooLongNicknameException;

class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {"테스트", "aaaaaaaaaaaaaaaaaaaa"})
    void 닉네임을_생성한다(final String rawNickname) {
        final Nickname nickname = Nickname.from(rawNickname);

        assertThat(nickname.getValue()).isEqualTo(rawNickname);
    }

    @Test
    void 닉네임이_20_자가_넘으면_예외가_발생한다() {
        assertThatThrownBy(() -> Nickname.from("a".repeat(21)))
                .isInstanceOf(TooLongNicknameException.class);
    }
}
