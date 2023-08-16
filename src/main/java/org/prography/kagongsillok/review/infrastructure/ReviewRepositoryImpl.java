package org.prography.kagongsillok.review.infrastructure;

import static org.prography.kagongsillok.record.domain.QStudyRecord.studyRecord;
import static org.prography.kagongsillok.review.domain.QReview.review;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.review.domain.Review;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private static final int DEFAULT_SEARCH_RESULT_SIZE = 10;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findAllByMemberId(final Long memberId) {
        return queryFactory
                .selectFrom(review)
                .where(
                        review.memberId.eq(memberId),
                        isNotDeleted()
                )
                .limit(DEFAULT_SEARCH_RESULT_SIZE)
                .fetch();
    }

    @Override
    public List<Review> findAllByPlaceId(final Long placeId) {
        return queryFactory
                .selectFrom(review)
                .where(
                        review.placeId.eq(placeId),
                        isNotDeleted()
                )
                .limit(DEFAULT_SEARCH_RESULT_SIZE)
                .fetch();
    }

    @Override
    public List<Review> findByReviewTagIds(final List<Long> reviewTagIds) {
        List<Review> allReviews = queryFactory
                .selectFrom(review)
                .where(
                        isNotDeleted()
                )
                .fetch();

        return allReviews.stream()
                .filter(rev -> rev.getTagMappings()
                        .getValues()
                        .stream()
                        .filter(reviewTagMapping -> reviewTagIds.contains(
                                reviewTagMapping
                                        .getReviewTag()
                                        .getId()
                        ))
                        .collect(Collectors.toList())
                        .size() > 0)
                .collect(Collectors.toList());
    }

    private BooleanExpression isNotDeleted() {
        return review.isDeleted.eq(Boolean.FALSE);
    }
}
