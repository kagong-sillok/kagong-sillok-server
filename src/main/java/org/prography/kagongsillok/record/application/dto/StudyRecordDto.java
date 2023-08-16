package org.prography.kagongsillok.record.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.record.domain.StudyRecord;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyRecordDto {

    private Long id;
    private String placeName;
    private LocalDate studyDate;
    private String description;
    private int duration;
    private List<Image> images;
    private ZonedDateTime writtenAt;

    @Builder
    public StudyRecordDto(
            final Long id,
            final String placeName,
            final LocalDate studyDate,
            final String description,
            final int duration,
            final List<Image> images,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.placeName = placeName;
        this.studyDate = studyDate;
        this.description = description;
        this.duration = duration;
        this.images = images;
        this.writtenAt = writtenAt;
    }

    public static StudyRecordDto of(final StudyRecord studyRecord, final List<Image> images) {
        return StudyRecordDto.builder()
                .id(studyRecord.getId())
                .placeName(studyRecord.getPlaceName())
                .studyDate(studyRecord.getStudyDate())
                .duration(studyRecord.getDuration())
                .description(studyRecord.getDescription())
                .images(images)
                .writtenAt(studyRecord.getWrittenAt())
                .build();
    }
}
