package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.record.domain.StudyRecord;

public class StudyRecordTest {

    @Test
    void 공부_기록을_생성한다() {
        final Long memberId = 1L;
        final Long placeId = 1L;
        final StudyRecord studyRecord = StudyRecord
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName("place1")
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(10)
                .duration(120)
                .description("모각코")
                .imageIds(List.of(1L, 2L))
                .build();

        assertAll(
                () -> assertThat(studyRecord.getMemberId()).isEqualTo(memberId),
                () -> assertThat(studyRecord.getPlaceId()).isEqualTo(placeId),
                () -> assertThat(studyRecord.getPlaceName()).isEqualTo("place1"),
                () -> assertThat(studyRecord.getStudyDate()).isEqualTo(LocalDate.of(2023, 7, 10)),
                () -> assertThat(studyRecord.getDuration()).isEqualTo(120),
                () -> assertThat(studyRecord.getDescription()).isEqualTo("모각코"),
                () -> assertThat(studyRecord.getImageIds()).isEqualTo(List.of(1L, 2L))
        );
    }
}
