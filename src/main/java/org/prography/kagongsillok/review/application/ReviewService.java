package org.prography.kagongsillok.review.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDto createReview(final ReviewCreateCommand reviewCreateCommand) {
        final Review savedReview = reviewRepository.save(reviewCreateCommand.toEntity());

        return ReviewDto.from(savedReview);
    }

    public ReviewDto getReview(final Long id) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));
        if (review.getIsDeleted()) {
            throw new NotFoundReviewException(id);
        }

        return ReviewDto.from(review);
    }

    public List<ReviewDto> getAllReviews(final Long id) {
        final List<Review> reviews = reviewRepository.findAllById(id);

        return CustomListUtils.mapTo(reviews, ReviewDto::from);
    }

    @Transactional
    public ReviewDto updateReview(final ReviewUpdateCommand reviewUpdateCommand) {
        final Long reviewId = reviewUpdateCommand.getId();

        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewException(reviewId));

        final Review target = reviewUpdateCommand.toEntity();

        review.update(target);

        return ReviewDto.from(review);
    }

    @Transactional
    public void deleteReview(final Long id) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));

        review.delete();
    }

}
