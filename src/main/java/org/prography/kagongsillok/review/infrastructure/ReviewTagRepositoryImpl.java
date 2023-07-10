package org.prography.kagongsillok.review.infrastructure;

import static org.prography.kagongsillok.review.domain.QReviewTag.reviewTag;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewTagRepositoryImpl implements ReviewTagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewTag> findAllTags() {
        return queryFactory
                .selectFrom(reviewTag)
                .where(
                        isNotDeleted()
                )
                .fetch();
    }

    @Override
    public List<ReviewTag> findByIdIn(final List<Long> ids) {
        return queryFactory
                .selectFrom(reviewTag)
                .where(
                        idIn(ids),
                        isNotDeleted()
                )
                .fetch();
    }

    @Override
    public Map<Long, ReviewTag> findByPerId(final List<Long> ids) {
        return queryFactory.selectFrom(reviewTag)
                .where(
                        idIn(ids),
                        isNotDeleted()
                )
                .stream()
                .collect(Collectors.toMap(ReviewTag::getId, Function.identity()));
    }

    private BooleanExpression idIn(final List<Long> ids) {
        return reviewTag.id.in(ids);
    }

    private BooleanExpression isNotDeleted() {
        return reviewTag.isDeleted.eq(Boolean.FALSE);
    }
}
