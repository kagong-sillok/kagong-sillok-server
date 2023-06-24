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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Nickname nickname;
    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isDeleted = Boolean.FALSE;

    public Member(final String nickname, final String email, final Role role) {
        this.nickname =  Nickname.from(nickname);
        this.email = Email.from(email);
        this.role = role;
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }
}
