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
    @JoinColumn(name = "authentication_id")
    private Authentication authentication;

    private Boolean isDeleted = Boolean.FALSE;

    public LocalAccount(
            final String loginId,
            final String encryptedPassword,
            final Authentication authentication
    ) {
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.authentication = authentication;
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }
}
