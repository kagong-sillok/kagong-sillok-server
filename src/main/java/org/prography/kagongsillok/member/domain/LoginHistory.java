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

    private LoginHistory(final LocalDate latestLoginDate, final int loginCount) {
        this.latestLoginDate = latestLoginDate;
        this.loginCount = loginCount;
    }

    public static LoginHistory from() {
        return new LoginHistory(LocalDate.now(), 1);
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
