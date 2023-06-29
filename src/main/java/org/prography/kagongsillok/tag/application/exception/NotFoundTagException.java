package org.prography.kagongsillok.tag.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public class NotFoundTagException extends NotFoundException {

    public NotFoundTagException(final Long tagId) {
        super(String.format("존재하지 않는 태그 Id가 있습니다. tagId = %d", tagId));
    }
}
