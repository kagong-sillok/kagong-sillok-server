package org.prography.kagongsillok.member.domain;

import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Nickname nickname;
    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String profileImage;

    @Embedded
    private LoginHistory loginHistory;

    public Member(final String nickname, final String email, final Role role) {
        this.nickname = Nickname.from(nickname);
        this.email = Email.from(email);
        this.role = role;
        this.profileImage = null;
        this.loginHistory = LoginHistory.from();
    }

    public Member(final String nickname, final String email, final Role role, String profileImage) {
        this.nickname = Nickname.from(nickname);
        this.email = Email.from(email);
        this.role = role;
        this.profileImage = profileImage;
        this.loginHistory = LoginHistory.from();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }

    public int getLoginCount() {
        return loginHistory.getLoginCount();
    }
}
