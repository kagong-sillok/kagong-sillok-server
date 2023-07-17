package org.prography.kagongsillok.record.domain.vo;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
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

    @Column(name = "study_date")
    private LocalDate value;

    private StudyRecordStudyDate(final LocalDate value) {
        this.value = value;
    }

    public static StudyRecordStudyDate of(final int year, final int month, final int day) {
        return new StudyRecordStudyDate(validateValue(year, month, day));
    }

    private static LocalDate validateValue(final int year, final int month, final int day) {
        LocalDate studyDate;
        try {
            studyDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new InvalidStudyDateException(year, month, day);
        }
        return studyDate;
    }
}
