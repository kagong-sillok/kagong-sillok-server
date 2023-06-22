package org.prography.kagongsillok.member.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public final class NotFoundMemberException extends NotFoundException {

    public NotFoundMemberException(final Long memberId) {
        super(String.format("존재하지 않는 회원입니다. memberId = %d", memberId));
    }
}
