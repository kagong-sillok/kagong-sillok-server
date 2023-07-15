package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyMonthException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyMonth;

public class StudyRecordStudyMonthTest {

    @ParameterizedTest
    @ValueSource(ints = {8, 11})
    void 공부한_년도와_월을_생성한다(final int rawMonth) {
        final StudyRecordStudyMonth studyRecordStudyMonth = StudyRecordStudyMonth.from(rawMonth);

        assertThat(studyRecordStudyMonth.getIntValue()).isEqualTo(rawMonth);
    }

    @Test
    void 공부한_월이_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyMonth.from(0))
                .isInstanceOf(InvalidStudyMonthException.class);
    }

    @Test
    void 공부한_월이_12보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyMonth.from(13))
                .isInstanceOf(InvalidStudyMonthException.class);
    }
}
