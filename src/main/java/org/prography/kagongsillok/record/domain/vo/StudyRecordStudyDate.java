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
    private String value;

    private StudyRecordStudyDate(final String value) {
        this.value = value;
    }

    public static StudyRecordStudyDate from(final String value) {
        validateValue(value);
        return new StudyRecordStudyDate(value);
    }

    private static void validateValue(final String value) {
        if (isUnderMinDate(value) || isOverMaxDate(value)) {
            throw new InvalidStudyDateException(value);
        }
    }

    private static boolean isUnderMinDate(final String value) {
        return Integer.parseInt(value) < MIN_DATE;
    }

    private static boolean isOverMaxDate(final String value) {
        return Integer.parseInt(value) > MAX_DATE;
    }
}
