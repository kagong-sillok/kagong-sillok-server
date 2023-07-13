package org.prography.kagongsillok.record.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public final class NotFoundStudyRecordException extends NotFoundException {

    public NotFoundStudyRecordException(final Long studyRecordId) {
        super(String.format("공부 기록이 존재하지 않습니다. studyRecordId = %d", studyRecordId));
    }
}
