package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyMonthException;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyYearException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyMonth;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyYear;

public class StudyRecordStudyYearTest {

    @ParameterizedTest
    @ValueSource(ints = {2019, 2023})
    void 공부한_년도를_생성한다(final int rawYear) {
        final StudyRecordStudyYear studyRecordStudyYear = StudyRecordStudyYear.from(rawYear);

        assertThat(studyRecordStudyYear.getIntValue()).isEqualTo(rawYear);
    }

    @Test
    void 공부한_년도가_0보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyYear.from(-1))
                .isInstanceOf(InvalidStudyYearException.class);
    }
}
