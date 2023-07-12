package org.prography.kagongsillok.record.infrastructure;

import static org.prography.kagongsillok.record.domain.QRecord.record;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public List<ZonedDateTime> findRecordByYearMonth(final Long memberId, final String yearMonth) {
        return queryFactory
                .select(record.writtenAt)
                .from(record)
                .where(
                        record.memberId.eq(memberId),
                        compareDate(record.writtenAt, yearMonth)
                )
                .fetch();
    }

    private BooleanExpression compareDate(final DateTimePath<ZonedDateTime> writtenAt, final String yearMonth) {
        return writtenAt.yearMonth().eq(Integer.parseInt(yearMonth));
    }
}
