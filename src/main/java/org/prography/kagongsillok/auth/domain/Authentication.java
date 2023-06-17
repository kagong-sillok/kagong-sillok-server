package org.prography.kagongsillok.auth.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
@Table(name = "authentication")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private AuthType authType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean isDeleted = Boolean.FALSE;

    public Authentication(final AuthType authType, final Member member) {
        this.authType = authType;
        this.member = member;
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }
}
