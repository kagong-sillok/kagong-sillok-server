package org.prography.kagongsillok.review.infrastructure;

import static org.prography.kagongsillok.image.domain.QImage.image;
import static org.prography.kagongsillok.review.domain.QReview.review;
import static org.prography.kagongsillok.review.domain.QReviewTag.reviewTag;
import static org.prography.kagongsillok.review.domain.QReviewTagMapping.reviewTagMapping;

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
                .leftJoin(review.tagMappings.values, reviewTagMapping)
                .fetchJoin()
                .leftJoin(reviewTagMapping.reviewTag, reviewTag)
                .fetchJoin()
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
                .leftJoin(review.tagMappings.values, reviewTagMapping)
                .fetchJoin()
                .leftJoin(reviewTagMapping.reviewTag, reviewTag)
                .fetchJoin()
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

    @Override
    public List<Review> findByPlaceIds(final List<Long> placeIds) {
        return queryFactory.selectFrom(review)
                .leftJoin(review.tagMappings.values, reviewTagMapping)
                .fetchJoin()
                .leftJoin(reviewTagMapping.reviewTag, reviewTag)
                .fetchJoin()
                .where(
                        idIn(placeIds),
                        isNotDeleted()
                )
                .stream()
                .collect(Collectors.toList());
    }

    private BooleanExpression idIn(final List<Long> ids) {
        return review.id.in(ids);
    }

    private BooleanExpression isNotDeleted() {
        return review.isDeleted.eq(Boolean.FALSE);
    }
}
