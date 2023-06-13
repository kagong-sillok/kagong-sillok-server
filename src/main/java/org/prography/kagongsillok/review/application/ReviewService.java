package org.prography.kagongsillok.review.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
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

}
