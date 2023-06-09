package org.prography.kagongsillok.review.infrastructure;

import static org.prography.kagongsillok.review.domain.QReview.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
                        review.isDeleted.eq(Boolean.FALSE)
                )
                .limit(DEFAULT_SEARCH_RESULT_SIZE)
                .fetch();
    }
}
