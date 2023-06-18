package org.prography.kagongsillok.auth.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.domain.Member;

@Getter
@Entity
@Table(name = "local_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String encryptedPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean isDeleted = Boolean.FALSE;

    public LocalAccount(
            final String loginId,
            final String encryptedPassword,
            final Member member
    ) {
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.member = member;
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }
}
