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

    private static final int MAX_HOUR = 23;
    private static final int MIN_HOUR = 0;
    private static final int MAX_MINUTE = 59;
    private static final int MIN_MINUTE = 0;

    @Column(name = "duration")
    private String value;

    private StudyRecordDuration(final String value) {
        this.value = value;
    }

    public static StudyRecordDuration from(final String value) {
        validateValue(value);
        return new StudyRecordDuration(value);
    }

    private static void validateValue(final String value) {
        final String[] values = value.split(":");
        if (isUnderMinHour(values[0]) || isOverMaxHour(values[0])
                || isUnderMinMinute(values[1]) || isOverMaxMinute(values[1])) {
            throw new InvalidStudyDurationBoundException(value);
        }
    }

    private static boolean isUnderMinHour(final String hourValue) {
        return Integer.parseInt(hourValue) < MIN_HOUR;
    }

    private static boolean isOverMaxHour(final String hourValue) {
        return Integer.parseInt(hourValue) > MAX_HOUR;
    }

    private static boolean isUnderMinMinute(final String minuteValue) {
        return Integer.parseInt(minuteValue) < MIN_MINUTE;
    }

    private static boolean isOverMaxMinute(final String minuteValue) {
        return Integer.parseInt(minuteValue) > MAX_MINUTE;
    }
}
