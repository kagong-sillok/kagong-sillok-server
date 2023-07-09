package org.prography.kagongsillok.record.ui.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.application.dto.RecordDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordResponse {

    private Long id;
    private String placeName;
    private String description;
    private String duration;
    private List<Long> imageIds;
    private ZonedDateTime createdAt;

    @Builder
    public RecordResponse(
            final Long id,
            final String placeName,
            final String description,
            final String duration,
            final List<Long> imageIds,
            final ZonedDateTime createdAt
    ) {
        this.id = id;
        this.placeName = placeName;
        this.description = description;
        this.duration = duration;
        this.imageIds = imageIds;
        this.createdAt = createdAt;
    }

    public static RecordResponse from(final RecordDto recordDto) {
        return RecordResponse.builder()
                .id(recordDto.getId())
                .placeName(recordDto.getPlaceName())
                .duration(recordDto.getDuration())
                .description(recordDto.getDescription())
                .imageIds(recordDto.getImageIds())
                .createdAt(recordDto.getCreatedAt())
                .build();
    }
}
