package org.prography.kagongsillok.review.infrastructure;

import java.util.List;
import org.prography.kagongsillok.review.domain.Review;

public interface ReviewRepositoryCustom {

    List<Review> findAllByMemberId(final Long memberId);

    List<Review> findAllByPlaceId(final Long placeId);

    List<Review> findByReviewTagIds(final List<Long> reviewTagIds);
}
