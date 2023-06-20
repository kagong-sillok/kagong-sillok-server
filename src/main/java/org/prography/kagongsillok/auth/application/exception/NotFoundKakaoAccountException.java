package org.prography.kagongsillok.auth.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public final class NotFoundKakaoAccountException extends NotFoundException {

    public NotFoundKakaoAccountException(final Long kakaoId) {
        super(String.format("존재하지 않는 카카오 계정입니다. kakaoId = %s", kakaoId));
    }
}
