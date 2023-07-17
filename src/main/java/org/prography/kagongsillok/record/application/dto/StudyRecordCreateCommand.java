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
    private int studyYear;
    private int studyMonth;
    private int studyDay;
    private String duration;
    private String description;
    private List<Long> imageIds;

    @Builder
    public StudyRecordCreateCommand(
            final Long memberId,
            final Long placeId,
            final int studyYear,
            final int studyMonth,
            final int studyDay,
            final String duration,
            final String description,
            final List<Long> imageIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.studyYear = studyYear;
        this.studyMonth = studyMonth;
        this.studyDay = studyDay;
        this.duration = duration;
        this.description = description;
        this.imageIds = imageIds;
    }

    public StudyRecord toEntity(final String placeName) {
        return StudyRecord.builder()
                .memberId(memberId)
                .placeId(placeId)
                .placeName(placeName)
                .studyYear(studyYear)
                .studyMonth(studyMonth)
                .studyDay(studyDay)
                .duration(duration)
                .description(description)
                .imageIds(imageIds)
                .build();
    }
}
