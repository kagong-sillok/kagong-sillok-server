package org.prography.kagongsillok.record.infrastructure;

import static org.prography.kagongsillok.record.domain.QStudyRecord.studyRecord;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Month;
import java.time.Year;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.record.domain.StudyRecord;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyRecordRepositoryImpl implements StudyRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StudyRecord> findMemberRecordByMemberId(final Long memberId) {
        return queryFactory
                .selectFrom(studyRecord)
                .where(
                        studyRecord.memberId.eq(memberId),
                        isNotDeleted()
                )
                .fetch();
    }

    @Override
    public List<StudyRecord> findMemberRecordByMemberIdAndYearMonth(final Long memberId, final int year, final int month) {
        return queryFactory
                .selectFrom(studyRecord)
                .where(
                        studyRecord.memberId.eq(memberId),
                        checkYearAndMonth(year, month),
                        isNotDeleted()
                )
                .fetch();
    }

    private BooleanExpression isNotDeleted() {
        return studyRecord.isDeleted.eq(Boolean.FALSE);
    }

    private BooleanExpression checkYearAndMonth(final int year, final int month) {
        return studyRecord.studyDate.value.year().eq(year).and(studyRecord.studyDate.value.month().eq(month));
    }
}
