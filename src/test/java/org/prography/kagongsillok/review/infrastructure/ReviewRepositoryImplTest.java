package org.prography.kagongsillok.review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRepositoryImplTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewRepositoryImpl reviewRepositoryImpl;

    @Test
    void 리뷰_ID로_리뷰를_조회한다() {
        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
                .builder()
                .rating(3)
                .memberId(3L)
                .content("test review1")
                .imageIds(List.of(1L, 2L, 3L))
                .tagIds(List.of(1L, 2L, 3L))
                .build();
        final Long savedReviewId = reviewRepository.save(reviewCreateCommand.toEntity()).getId();

        final Review savedReview = reviewRepository.findById(savedReviewId)
                .orElseThrow(() -> new NotFoundReviewException(savedReviewId));

        assertAll(
                () -> assertThat(savedReview.getRating()).isEqualTo(3),
                () -> assertThat(savedReview.getMemberId()).isEqualTo(3L),
                () -> assertThat(savedReview.getContent()).isEqualTo("test review1"),
                () -> assertThat(savedReview.getImageIds()).containsAll(List.of(1L, 2L, 3L)),
                () -> assertThat(savedReview.getTags().getReviewTags().size()).isEqualTo(0)
        );
    }

    @Test
    void 멤버_ID로_작성한_리뷰들을_조회한다() {
        final ReviewCreateCommand reviewCreateCommand1 = ReviewCreateCommand
                .builder()
                .rating(4)
                .memberId(5L)
                .content("test review1")
                .imageIds(List.of(1L, 2L))
                .tagIds(List.of(1L, 2L))
                .build();
        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
                .builder()
                .rating(2)
                .memberId(5L)
                .content("test review2")
                .imageIds(List.of(1L, 3L))
                .tagIds(List.of(1L, 2L))
                .build();
        final ReviewCreateCommand reviewCreateCommand3 = ReviewCreateCommand
                .builder()
                .rating(5)
                .memberId(5L)
                .content("test review3")
                .imageIds(List.of(2L, 3L))
                .tagIds(List.of(1L, 2L))
                .build();
        final ReviewCreateCommand reviewCreateCommand4 = ReviewCreateCommand
                .builder()
                .rating(1)
                .memberId(5L)
                .content("test review4")
                .imageIds(List.of(4L))
                .tagIds(List.of(1L, 2L))
                .build();
        reviewRepository.save(reviewCreateCommand1.toEntity());
        reviewRepository.save(reviewCreateCommand2.toEntity());
        reviewRepository.save(reviewCreateCommand3.toEntity());
        reviewRepository.save(reviewCreateCommand4.toEntity());

        final List<Review> reviews = reviewRepositoryImpl.findAllByMemberId(5L);

        assertAll(
                () -> assertThat(reviews.size()).isEqualTo(4),
                () -> assertThat(reviews).extracting("memberId").containsAll(List.of(5L, 5L, 5L, 5L)),
                () -> assertThat(reviews).extracting("rating").containsAll(List.of(4, 2, 5, 1)),
                () -> assertThat(reviews).extracting("imageIds")
                        .containsAll(List.of(List.of(1L, 2L), List.of(1L, 3L), List.of(2L, 3L), List.of(4L))),
                () -> assertThat(reviews).extracting("content")
                        .containsAll(List.of("test review1", "test review2", "test review3", "test review4"))
        );
    }

}
