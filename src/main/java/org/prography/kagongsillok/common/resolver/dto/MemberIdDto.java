package org.prography.kagongsillok.common.resolver.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberIdDto {
    private Long memberId;

    public MemberIdDto(final Long memberId) {
        this.memberId = memberId;
    }
}
