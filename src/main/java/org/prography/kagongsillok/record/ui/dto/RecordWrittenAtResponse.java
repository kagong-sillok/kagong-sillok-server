package org.prography.kagongsillok.record.ui.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.application.dto.RecordWrittenAtDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordWrittenAtResponse {

    private String writtenDate;

    public static RecordWrittenAtResponse from(RecordWrittenAtDto recordWrittenAtDto) {
        return new RecordWrittenAtResponse(recordWrittenAtDto.getWrittenDate());
    }
}
