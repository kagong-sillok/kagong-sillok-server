package org.prography.kagongsillok.member.infrastructure;

import static org.prography.kagongsillok.member.domain.QMember.member;
import static org.prography.kagongsillok.place.domain.QPlace.place;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.member.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findByIdIn(final List<Long> memberIds) {
        return queryFactory
                .selectFrom(member)
                .where(
                        idIn(memberIds),
                        isNotDeleted()
                )
                .fetch();
    }

    private BooleanExpression idIn(final List<Long> ids) {
        return member.id.in(ids);
    }

    private BooleanExpression isNotDeleted() {
        return member.isDeleted.eq(Boolean.FALSE);
    }
}
