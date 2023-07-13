package org.prography.kagongsillok.record.infrastructure;

import java.util.List;
import org.prography.kagongsillok.record.domain.StudyRecord;

public interface StudyRecordRepositoryCustom {

    List<StudyRecord> findMemberRecordByMemberId(final Long memberId);

}
