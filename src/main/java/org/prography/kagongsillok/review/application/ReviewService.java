package org.prography.kagongsillok.review.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.ReviewTag.domain.ReviewTagRepository;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.prography.kagongsillok.ReviewTag.domain.ReviewTag;
import org.prography.kagongsillok.tag.domain.Tag;
import org.prography.kagongsillok.tag.domain.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;

    @Transactional
    public ReviewDto createReview(final ReviewCreateCommand reviewCreateCommand) {
        List<Tag> tags = tagRepository.findByIdIn(reviewCreateCommand.getTagIds());
        final Review review = reviewCreateCommand.toEntity();

        for (Tag tag : tags) {
             reviewTagRepository.save(new ReviewTag(review, tag));
        }

        final Review savedReview = reviewRepository.save(review);

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

    public List<ReviewDto> getAllReviewsByMemberId(final Long memberId) {
        final List<Review> reviews = reviewRepository.findAllByMemberId(memberId);

        return CustomListUtils.mapTo(reviews, ReviewDto::from);
    }

    @Transactional
    public ReviewDto updateReview(final ReviewUpdateCommand reviewUpdateCommand) {
        final Long reviewId = reviewUpdateCommand.getId();

        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewException(reviewId));

        final List<ReviewTag> reviewTags = review.getTags().getReviewTags();
        for (ReviewTag reviewTag : reviewTags) {
            reviewTag.disconnect();
            reviewTagRepository.delete(reviewTag);
        }

        List<Tag> tags = tagRepository.findByIdIn(reviewUpdateCommand.getTagIds());
        for (Tag tag : tags) {
            reviewTagRepository.save(new ReviewTag(review, tag));
        }

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
