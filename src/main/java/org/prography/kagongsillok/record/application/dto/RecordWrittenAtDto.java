package org.prography.kagongsillok.record.application.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.record.domain.Record;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordWrittenAtDto {

    private String writtenDate;

    private RecordWrittenAtDto(final String writtenDate) {
        this.writtenDate = writtenDate;
    }

    public static RecordWrittenAtDto from(final ZonedDateTime zonedDateTime) {
        return new RecordWrittenAtDto(toDate(zonedDateTime));
    }

    private static String toDate(final ZonedDateTime writtenAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return writtenAt.format(formatter);
    }
}
