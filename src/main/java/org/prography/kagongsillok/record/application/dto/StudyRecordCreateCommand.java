package org.prography.kagongsillok.record.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.StudyRecord;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyRecordCreateCommand {

    private Long memberId;
    private Long placeId;
    private String duration;
    private String description;
    private List<Long> imageIds;

    @Builder
    public StudyRecordCreateCommand(
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

    public StudyRecord toEntity(final String placeName) {
        return StudyRecord.builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName(placeName)
                .duration(duration)
                .description(description)
                .imageIds(imageIds)
                .build();
    }
}
