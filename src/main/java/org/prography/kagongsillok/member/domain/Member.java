package org.prography.kagongsillok.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;

@Getter
@Entity
@Table(name = "member")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update member set is_deleted = true, updated_at = now() where id = ?")
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

    private String profileImageUrl;

    @Embedded
    private LoginHistory loginHistory;

    @Builder
    public Member(final String nickname, final String email, final Role role, String profileImageUrl) {
        this.nickname = Nickname.from(nickname);
        this.email = Email.from(email);
        this.role = role;
        this.profileImageUrl = profileImageUrl;
        this.loginHistory = LoginHistory.init();
    }

    public static Member defaultOf() {
        return Member.builder()
                .nickname("알 수 없음")
                .email("Unknown@unknown.com")
                .build();
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

    public void loginHistoryUpdate() {
        loginHistory.loginHistoryUpdate();
    }
}
