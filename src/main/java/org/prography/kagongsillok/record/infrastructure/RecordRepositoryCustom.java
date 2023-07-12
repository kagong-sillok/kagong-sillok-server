package org.prography.kagongsillok.record.infrastructure;

import java.util.List;
import org.prography.kagongsillok.record.domain.Record;

public interface RecordRepositoryCustom {

    List<Record> findMemberRecordByMemberId(Long memberId);

}
