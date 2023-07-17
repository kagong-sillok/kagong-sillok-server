package org.prography.kagongsillok.record.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyDurationBoundException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecordDuration {

    private static final int MIN_MINUTE = 0;

    @Column(name = "duration")
    private int value;

    private StudyRecordDuration(final int value) {
        this.value = value;
    }

    public static StudyRecordDuration from(final int value) {
        validateValue(value);
        return new StudyRecordDuration(value);
    }

    private static void validateValue(final int value) {
        if (isUnderMinMinute(value)) {
            throw new InvalidStudyDurationBoundException(value);
        }
    }

    private static boolean isUnderMinMinute(final int value) {
        return value < MIN_MINUTE;
    }
}
