package org.prography.kagongsillok.record.domain.vo;

import java.time.Year;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyYearException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecordStudyYear {

    private static final int MIN_YEAR = 0;
    @Column(name = "study_year")
    private Year value;

    private StudyRecordStudyYear(final Year value) {
        this.value = value;
    }

    public static StudyRecordStudyYear from(final int intValue) {
        validateValue(intValue);
        return new StudyRecordStudyYear(toYear(intValue));
    }

    private static void validateValue(final int intValue) {
        if (isUnderMinYear(intValue)) {
            throw new InvalidStudyYearException(intValue);
        }
    }

    private static boolean isUnderMinYear(final int intValue) {
        return intValue < MIN_YEAR;
    }

    private static Year toYear(int intValue) {
        return Year.of(intValue);
    }

    public int getIntValue() {
        return value.getValue();
    }
}
