package org.prography.kagongsillok.record.application.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.StudyRecord;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyRecordDto {

    private Long id;
    private String placeName;
    private String studyDay;
    private String description;
    private String duration;
    private List<Long> imageIds;
    private ZonedDateTime writtenAt;

    @Builder
    public StudyRecordDto(
            final Long id,
            final String placeName,
            final String studyDay,
            final String description,
            final String duration,
            final List<Long> imageIds,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.placeName = placeName;
        this.studyDay = studyDay;
        this.description = description;
        this.duration = duration;
        this.imageIds = imageIds;
        this.writtenAt = writtenAt;
    }

    public static StudyRecordDto from(final StudyRecord studyRecord) {
        return StudyRecordDto.builder()
                .id(studyRecord.getId())
                .placeName(studyRecord.getPlaceName())
                .studyDay(getStudyDay(studyRecord))
                .duration(studyRecord.getDuration().getValue())
                .description(studyRecord.getDescription().getValue())
                .imageIds(studyRecord.getImageIds())
                .writtenAt(studyRecord.getWrittenAt())
                .build();
    }

    private static String getStudyDay(final StudyRecord studyRecord) {
        return studyRecord.getStudyYearMonth().getValue() + "." + studyRecord.getStudyDate().getValue();
    }
}
