package org.prography.kagongsillok.record.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyYearMonthException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecordStudyYearMonth {

    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;

    @Column(name = "study_year_month")
    private String value;

    private StudyRecordStudyYearMonth(final String value) {
        this.value = value;
    }

    public static StudyRecordStudyYearMonth from(final String value) {
        validateValue(value);
        return new StudyRecordStudyYearMonth(value);
    }

    private static void validateValue(final String value) {
        String[] splitValue = value.split("\\.");
        if (isYearMonth(splitValue) || isTwoDigitMonth(splitValue[1])
                || isUnderMinMonth(splitValue[1]) || isOverMaxMonth(splitValue[1])) {
            throw new InvalidStudyYearMonthException(value);
        }
    }

    private static boolean isYearMonth(final String[] splitValue) {
        if (splitValue.length != 2) {
            return true;
        }

        return false;
    }

    private static boolean isTwoDigitMonth(final String monthValue) {
        if (monthValue.length() != 2) {
            return true;
        }

        return false;
    }

    private static boolean isUnderMinMonth(final String monthValue) {
        return Integer.parseInt(monthValue) < MIN_MONTH;
    }

    private static boolean isOverMaxMonth(final String monthValue) {
        return Integer.parseInt(monthValue) > MAX_MONTH;
    }
}
