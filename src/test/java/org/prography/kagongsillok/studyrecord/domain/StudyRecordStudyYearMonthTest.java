package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyYearMonthException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyYearMonth;

public class StudyRecordStudyYearMonthTest {

    @ParameterizedTest
    @ValueSource(strings = {"2019.07", "2023.08"})
    void 공부한_년도와_월을_생성한다(final String rawYearMonth) {
        final StudyRecordStudyYearMonth studyRecordStudyYearMonth = StudyRecordStudyYearMonth.from(rawYearMonth);

        assertThat(studyRecordStudyYearMonth.getValue()).isEqualTo(rawYearMonth);
    }

    @Test
    void 공부한_월이_두글자가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyYearMonth.from("8"))
                .isInstanceOf(InvalidStudyYearMonthException.class);
    }

    @Test
    void 공부한_월이_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyYearMonth.from("00"))
                .isInstanceOf(InvalidStudyYearMonthException.class);
    }

    @Test
    void 공부한_월이_12보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyYearMonth.from("13"))
                .isInstanceOf(InvalidStudyYearMonthException.class);
    }
}
