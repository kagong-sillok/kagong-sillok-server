package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyDateException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyDate;

public class StudyRecordStudyDateTest {
    @ParameterizedTest
    @ValueSource(strings = {"10", "26"})
    void 공부한_일자를_생성한다(final String rawDate) {
        final StudyRecordStudyDate studyRecordStudyDate = StudyRecordStudyDate.from(rawDate);

        assertThat(studyRecordStudyDate.getValue()).isEqualTo(rawDate);
    }

    @Test
    void 공부한_일자가_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.from("0"))
                .isInstanceOf(InvalidStudyDateException.class);
    }

    @Test
    void 공부한_일자가_31보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.from("32"))
                .isInstanceOf(InvalidStudyDateException.class);
    }
}
