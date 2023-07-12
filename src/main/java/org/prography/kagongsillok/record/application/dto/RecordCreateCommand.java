package org.prography.kagongsillok.record.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.record.domain.Record;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordCreateCommand {

    private Long memberId;
    private Long placeId;
    private String duration;
    private String description;
    private List<Long> imageIds;

    @Builder
    public RecordCreateCommand(
            final Long memberId,
            final Long placeId,
            final String duration,
            final String description,
            final List<Long> imageIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.duration = duration;
        this.description = description;
        this.imageIds = imageIds;
    }

    public Record toEntity(final String placeName) {
        return Record.builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName(placeName)
                .duration(duration)
                .description(description)
                .imageIds(imageIds)
                .build();
    }
}
