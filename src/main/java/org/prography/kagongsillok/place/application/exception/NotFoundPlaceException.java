package org.prography.kagongsillok.place.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public class NotFoundPlaceException extends NotFoundException {

    public NotFoundPlaceException(final Long placeId) {
        super(String.format("장소가 존재하지 않습니다. placeId = %d", placeId));
    }
}
