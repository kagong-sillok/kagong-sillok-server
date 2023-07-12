package org.prography.kagongsillok.record.application.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.Record;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordDto {

    private Long id;
    private String placeName;
    private String description;
    private String duration;
    private List<Long> imageIds;
    private ZonedDateTime writtenAt;

    @Builder
    public RecordDto(
            final Long id,
            final String placeName,
            final String description,
            final String duration,
            final List<Long> imageIds,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.placeName = placeName;
        this.description = description;
        this.duration = duration;
        this.imageIds = imageIds;
        this.writtenAt = writtenAt;
    }

    public static RecordDto from(final Record record) {
        return RecordDto.builder()
                .id(record.getId())
                .placeName(record.getPlaceName())
                .description(record.getDescription())
                .duration(record.getDuration())
                .imageIds(record.getImageIds())
                .writtenAt(record.getWrittenAt())
                .build();
    }
}
