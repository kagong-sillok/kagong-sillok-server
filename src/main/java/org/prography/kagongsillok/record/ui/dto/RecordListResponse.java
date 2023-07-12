package org.prography.kagongsillok.record.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.record.application.dto.RecordDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordListResponse {

    private List<RecordResponse> records;

    public static RecordListResponse of(final List<RecordDto> recordDtos) {
        return new RecordListResponse(CustomListUtils.mapTo(recordDtos, RecordResponse::from));
    }
}
