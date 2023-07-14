package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyDurationBoundException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordDuration;

public class StudyRecordDurationTest {

    @ParameterizedTest
    @ValueSource(strings = {"10:00", "03:49"})
    void 공부_시간을_생성한다(final String rawDuration) {
        final StudyRecordDuration studyRecordDuration = StudyRecordDuration.from(rawDuration);

        assertThat(studyRecordDuration.getValue()).isEqualTo(rawDuration);
    }

    @Test
    void 공부_시간의_시가_0보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordDuration.from("-1:10"))
                .isInstanceOf(InvalidStudyDurationBoundException.class);
    }

    @Test
    void 공부_시간의_시가_23보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordDuration.from("24:00"))
                .isInstanceOf(InvalidStudyDurationBoundException.class);
    }

    @Test
    void 공부_시간의_분이_0보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordDuration.from("13:-1"))
                .isInstanceOf(InvalidStudyDurationBoundException.class);
    }

    @Test
    void 공부_시간의_분이_59보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordDuration.from("10:60"))
                .isInstanceOf(InvalidStudyDurationBoundException.class);
    }
}
