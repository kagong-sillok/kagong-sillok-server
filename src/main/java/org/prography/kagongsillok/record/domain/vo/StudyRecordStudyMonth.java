package org.prography.kagongsillok.record.domain.vo;

import java.time.Month;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyMonthException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecordStudyMonth {

    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;

    @Column(name = "study_month")
    private Month value;

    private StudyRecordStudyMonth(final Month value) {
        this.value = value;
    }

    public static StudyRecordStudyMonth from(final int intValue) {
        validateValue(intValue);
        return new StudyRecordStudyMonth(toMonth(intValue));
    }

    private static void validateValue(final int intValue) {
        if (isUnderMinMonth(intValue) || isOverMaxMonth(intValue)) {
            throw new InvalidStudyMonthException(intValue);
        }
    }

    private static boolean isUnderMinMonth(final int intValue) {
        return intValue < MIN_MONTH;
    }

    private static boolean isOverMaxMonth(final int intValue) {
        return intValue > MAX_MONTH;
    }

    private static Month toMonth(int intValue) {
        return Month.of(intValue);
    }

    public int getIntValue() {
        return value.getValue();
    }
}
