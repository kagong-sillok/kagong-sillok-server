package org.prography.kagongsillok.record.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.record.application.dto.RecordWrittenAtDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordWrittenAtListResponse {

    private List<RecordWrittenAtResponse> timelines;

    public static RecordWrittenAtListResponse of(final List<RecordWrittenAtDto> recordWrittenAtDtos) {
        return new RecordWrittenAtListResponse(CustomListUtils.mapTo(recordWrittenAtDtos, RecordWrittenAtResponse::from));
    }

}
