package org.prography.kagongsillok.record.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.application.dto.StudyRecordCreateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyRecordCreateRequest {

    private Long memberId;
    private Long placeId;
    private int studyYear;
    private int studyMonth;
    private int studyDay;
    private int duration;
    private String description;
    private List<Long> imageIds;

    @Builder
    public StudyRecordCreateRequest(
            final Long memberId,
            final Long placeId,
            final int studyYear,
            final int studyMonth,
            final int studyDay,
            final int duration,
            final String description,
            final List<Long> imageIds) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.studyYear = studyYear;
        this.studyMonth = studyMonth;
        this.studyDay = studyDay;
        this.duration = duration;
        this.description = description;
        this.imageIds = imageIds;
    }

    public StudyRecordCreateCommand toCommand() {
        return StudyRecordCreateCommand.builder()
                .memberId(memberId)
                .placeId(placeId)
                .studyYear(studyYear)
                .studyMonth(studyMonth)
                .studyDay(studyDay)
                .duration(duration)
                .description(description)
                .imageIds(imageIds)
                .build();
    }
}
