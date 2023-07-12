package org.prography.kagongsillok.record.infrastructure;

import static org.prography.kagongsillok.record.domain.QRecord.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.record.domain.Record;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Record> findMemberRecordByMemberId(final Long memberId) {
        return queryFactory
                .selectFrom(record)
                .where(
                        record.memberId.eq(memberId)
                )
                .fetch();
    }
}
