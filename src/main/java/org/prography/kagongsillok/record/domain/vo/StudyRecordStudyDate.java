package org.prography.kagongsillok.record.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyDateException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecordStudyDate {

    private static final int MAX_DATE = 31;
    private static final int MIN_DATE = 1;

    @Column(name = "study_date")
    private int value;

    private StudyRecordStudyDate(final int value) {
        this.value = value;
    }

    public static StudyRecordStudyDate from(final int value) {
        validateValue(value);
        return new StudyRecordStudyDate(value);
    }

    private static void validateValue(final int value) {
        if (isUnderMinDate(value) || isOverMaxDate(value)) {
            throw new InvalidStudyDateException(value);
        }
    }

    private static boolean isUnderMinDate(final int value) {
        return value < MIN_DATE;
    }

    private static boolean isOverMaxDate(final int value) {
        return value > MAX_DATE;
    }
}
