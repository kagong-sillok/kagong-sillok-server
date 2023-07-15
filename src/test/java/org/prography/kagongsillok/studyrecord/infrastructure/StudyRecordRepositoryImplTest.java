package org.prography.kagongsillok.studyrecord.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.record.domain.StudyRecord;
import org.prography.kagongsillok.record.domain.StudyRecordRepository;
import org.prography.kagongsillok.record.infrastructure.StudyRecordRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudyRecordRepositoryImplTest {

    @Autowired
    private StudyRecordRepository studyRecordRepository;

    @Autowired
    private StudyRecordRepositoryImpl studyRecordRepositoryImpl;

    @Test
    void 멤버_ID로_공부_기록들을_조회한다() {
        final Long memberId = 1L;
        final Long placeId = 2L;
        final StudyRecord studyRecord1 = StudyRecord
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName("place2")
                .studyYear(2023)
                .studyMonth(7)
                .studyDate(10)
                .duration("10:00")
                .description("모각코1")
                .imageIds(List.of(1L, 2L))
                .build();
        final StudyRecord studyRecord2 = StudyRecord
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName("place2")
                .studyYear(2023)
                .studyMonth(7)
                .studyDate(10)
                .duration("10:00")
                .description("모각코2")
                .imageIds(List.of(1L, 2L))
                .build();
        final StudyRecord studyRecord3 = StudyRecord
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName("place2")
                .studyYear(2023)
                .studyMonth(7)
                .studyDate(10)
                .duration("10:00")
                .description("모각코3")
                .imageIds(List.of(1L, 2L))
                .build();
        studyRecordRepository.save(studyRecord1).getId();
        studyRecordRepository.save(studyRecord2).getId();
        studyRecordRepository.save(studyRecord3).getId();

        final List<StudyRecord> studyRecords = studyRecordRepositoryImpl.findMemberRecordByMemberId(memberId);

        assertAll(
                () -> assertThat(studyRecords.size()).isEqualTo(3),
                () -> assertThat(studyRecords).extracting("placeId")
                        .containsAll(List.of(2L, 2L, 2L)),
                () -> assertThat(studyRecords).extracting("placeName")
                        .containsAll(List.of("place2", "place2", "place2")),
                () -> assertThat(studyRecords).extracting("duration")
                        .containsAll(List.of("10:00", "10:00", "10:00")),
                () -> assertThat(studyRecords).extracting("description")
                        .containsAll(List.of("모각코1", "모각코2", "모각코3"))
        );
    }

    @Test
    void 멤버_ID와_년도와_월로_타임라인을_조회한다() {
        final Long memberId = 1L;
        final Long placeId = 2L;
        final StudyRecord studyRecord1 = StudyRecord
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName("place2")
                .studyYear(2022)
                .studyMonth(6)
                .studyDate(10)
                .duration("10:00")
                .description("모각코1")
                .imageIds(List.of(1L, 2L))
                .build();
        final StudyRecord studyRecord2 = StudyRecord
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName("place2")
                .studyYear(2022)
                .studyMonth(6)
                .studyDate(10)
                .duration("10:00")
                .description("모각코2")
                .imageIds(List.of(1L, 2L))
                .build();
        studyRecordRepository.save(studyRecord1).getId();
        studyRecordRepository.save(studyRecord2).getId();

        final List<StudyRecord> studyRecords = studyRecordRepositoryImpl.findMemberRecordByMemberIdAndYearMonth(
                memberId, 2022, 6);

        assertAll(
                () -> assertThat(studyRecords.size()).isEqualTo(2),
                () -> assertThat(studyRecords).extracting("placeId")
                        .containsAll(List.of(2L, 2L)),
                () -> assertThat(studyRecords).extracting("placeName")
                        .containsAll(List.of("place2", "place2")),
                () -> assertThat(studyRecords).extracting("duration")
                        .containsAll(List.of("10:00", "10:00")),
                () -> assertThat(studyRecords).extracting("description")
                        .containsAll(List.of("모각코1", "모각코2"))
        );
    }
}
