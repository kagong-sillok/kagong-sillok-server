package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class RefreshToken {

    private String id;
    private String value;
    private Long memberId;
    private ZonedDateTime expire;

    public RefreshToken(final String value, final Long memberId, final ZonedDateTime expire) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
        this.memberId = memberId;
        this.expire = expire;
    }
}
