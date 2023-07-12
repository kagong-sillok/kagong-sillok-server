package org.prography.kagongsillok.record.infrastructure;

import java.time.ZonedDateTime;
import java.util.List;
import org.prography.kagongsillok.record.domain.Record;

public interface RecordRepositoryCustom {

    List<Record> findMemberRecordByMemberId(final Long memberId);

    List<ZonedDateTime> findRecordByYearMonth(final Long memberId, final String date);

}
