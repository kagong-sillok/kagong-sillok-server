package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyDescriptionBoundException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordDescription;

public class StudyRecordDescriptionTest {

    @ParameterizedTest
    @ValueSource(strings = {"모각코", "모"})
    void 공부_내용을_생성한다(final String rawDescription) {
        final StudyRecordDescription studyRecordDescription = StudyRecordDescription.from(rawDescription);

        assertThat(studyRecordDescription.getValue()).isEqualTo(rawDescription);
    }

    @Test
    void 공부_내용_길이가_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordDescription.from(""))
                .isInstanceOf(InvalidStudyDescriptionBoundException.class);
    }

    @Test
    void 공부_내용_길이가_10보다_길면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordDescription.from("모".repeat(11)))
                .isInstanceOf(InvalidStudyDescriptionBoundException.class);
    }
}
