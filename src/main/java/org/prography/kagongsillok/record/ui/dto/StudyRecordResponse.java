package org.prography.kagongsillok.record.ui.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.record.application.dto.StudyRecordDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyRecordResponse {

    private Long id;
    private String placeName;
    private LocalDate studyDate;
    private String description;
    private int duration;
    private List<ImageDto> images;
    private ZonedDateTime writtenAt;

    @Builder
    public StudyRecordResponse(
            final Long id,
            final String placeName,
            final LocalDate studyDate,
            final String description,
            final int duration,
            final List<ImageDto> images,
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

    public static StudyRecordResponse from(final StudyRecordDto studyRecordDto) {
        return StudyRecordResponse.builder()
                .id(studyRecordDto.getId())
                .placeName(studyRecordDto.getPlaceName())
                .studyDate(studyRecordDto.getStudyDate())
                .duration(studyRecordDto.getDuration())
                .description(studyRecordDto.getDescription())
                .images(studyRecordDto.getImages())
                .writtenAt(studyRecordDto.getWrittenAt())
                .build();
    }
}
