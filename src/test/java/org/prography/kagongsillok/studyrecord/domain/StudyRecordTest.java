package org.prography.kagongsillok.studyrecord.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                .studyDate(10)
                .duration("10:00")
                .description("모각코")
                .imageIds(List.of(1L, 2L))
                .build();

        assertAll(
                () -> assertThat(studyRecord.getMemberId()).isEqualTo(memberId),
                () -> assertThat(studyRecord.getPlaceId()).isEqualTo(placeId),
                () -> assertThat(studyRecord.getPlaceName()).isEqualTo("place1"),
                () -> assertThat(studyRecord.getStudyYear()).isEqualTo(2023),
                () -> assertThat(studyRecord.getStudyMonth()).isEqualTo(7),
                () -> assertThat(studyRecord.getStudyDate()).isEqualTo(10),
                () -> assertThat(studyRecord.getDuration()).isEqualTo("10:00"),
                () -> assertThat(studyRecord.getDescription()).isEqualTo("모각코"),
                () -> assertThat(studyRecord.getImageIds()).isEqualTo(List.of(1L, 2L))
        );
    }
}
