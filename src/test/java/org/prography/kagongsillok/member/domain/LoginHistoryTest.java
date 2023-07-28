package org.prography.kagongsillok.member.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class LoginHistoryTest {

    @Test
    void 로그인_기록을_생성한다() {
        final LoginHistory loginHistory = LoginHistory.from();

        assertAll(
                () -> assertThat(loginHistory.getLatestLoginDate()).isEqualTo(LocalDate.now()),
                () -> assertThat(loginHistory.getLoginCount()).isEqualTo(1)
        );
    }
}
