package org.prography.kagongsillok.member.domain;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginHistory {

    private LocalDate latestLoginDate;
    private int loginCount;

    private LoginHistory(final LocalDate latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
        this.loginCount = 1;
    }

    public static LoginHistory from() {
        return new LoginHistory(LocalDate.now());
    }

    public void loginHistoryUpdate() {
        if (checkDate()) {
            latestLoginDate = LocalDate.now();
            loginCount += 1;
        }
    }

    private boolean checkDate() {
        return latestLoginDate.isBefore(LocalDate.now());
    }
}
