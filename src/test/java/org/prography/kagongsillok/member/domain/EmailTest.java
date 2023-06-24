package org.prography.kagongsillok.member.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.member.domain.exception.InvalidEmailException;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"test@test.com", "test@test.co.kr", "test@test.net", "test@test.ac.kr"})
    void 이메일을_생성한다(final String rawEmail) {
        final Email email = Email.from(rawEmail);

        assertThat(email.getValue()).isEqualTo(rawEmail);
    }
    
    @Test
    void 이메일_형식이_아닌_문자열로_이메일을_생성하려_하면_예외가_발생한다() {
        assertThatThrownBy(() -> Email.from("notEmailFormString"))
                .isInstanceOf(InvalidEmailException.class);
    }
}
