package org.prography.kagongsillok.record.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.record.application.dto.StudyRecordDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyRecordListResponse {

    private List<StudyRecordResponse> studyRecords;

    public static StudyRecordListResponse of(final List<StudyRecordDto> studyRecordDtos) {
        return new StudyRecordListResponse(CustomListUtils.mapTo(studyRecordDtos, StudyRecordResponse::from));
    }
}
