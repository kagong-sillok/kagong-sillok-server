package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.record.domain.exception.InvalidStudyDateException;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyDate;

public class StudyRecordStudyDateTest {

    private static final int MAX_YEAR = LocalDate.MAX.getYear();
    private static final int MIN_YEAR = LocalDate.MIN.getYear();

    @Test
    void 공부한_일자를_생성한다() {
        final StudyRecordStudyDate studyRecordStudyDate = StudyRecordStudyDate.of(2023, 7, 10);

        assertThat(studyRecordStudyDate.getValue()).isEqualTo(LocalDate.of(2023, 7, 10));
    }

    @Test
    void 공부한_년도가_최소값_보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.of(MIN_YEAR-1, 7, 10))
                .isInstanceOf(InvalidStudyDateException.class);
    }

    @Test
    void 공부한_년도가_최대값_보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.of(MAX_YEAR+1, 7, 10))
                .isInstanceOf(InvalidStudyDateException.class);
    }

    @Test
    void 공부한_월이_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.of(2023, 0, 10))
                .isInstanceOf(InvalidStudyDateException.class);
    }

    @Test
    void 공부한_월이_12보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.of(2023, 13, 10))
                .isInstanceOf(InvalidStudyDateException.class);
    }

    @Test
    void 공부한_일이_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.of(2023, 7, 0))
                .isInstanceOf(InvalidStudyDateException.class);
    }

    @Test
    void 공부한_일이_해당월의_마지막_날보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> StudyRecordStudyDate.of(2023, 6, 31))
                .isInstanceOf(InvalidStudyDateException.class);
    }
}
