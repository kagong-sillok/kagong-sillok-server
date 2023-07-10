package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_ID로_리뷰를_조회한다() {
        final Review review1 = createReviewOfMemberIdAndContent(1L, "test review");
        final Long savedReviewId = reviewRepository.save(review1).getId();

        final Review savedReview = reviewRepository.findById(savedReviewId)
                .orElseThrow(() -> new NotFoundReviewException(savedReviewId));

        assertAll(
                () -> assertThat(savedReview.getRating()).isEqualTo(5),
                () -> assertThat(savedReview.getMemberId()).isEqualTo(1L),
                () -> assertThat(savedReview.getContent()).isEqualTo("test review"),
                () -> assertThat(savedReview.getImageIds()).containsAll(List.of(1L, 2L, 3L)),
                () -> assertThat(savedReview.getTagMappings().getValues().size()).isEqualTo(0)
        );
    }

    @Test
    void 멤버_ID로_작성한_리뷰들을_조회한다() {
        final Review review1 = createReviewOfMemberIdAndContent(2L, "test review1");
        final Review review2 = createReviewOfMemberIdAndContent(2L, "test review2");
        final Review review3 = createReviewOfMemberIdAndContent(2L, "test review3");
        final Review review4 = createReviewOfMemberIdAndContent(2L, "test review4");
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);

        final List<Review> reviews = reviewRepository.findAllByMemberId(2L);

        assertAll(
                () -> assertThat(reviews.size()).isEqualTo(4),
                () -> assertThat(reviews).extracting("rating")
                        .containsAll(List.of(5, 5, 5, 5)),
                () -> assertThat(reviews).extracting("content")
                        .containsAll(List.of("test review1", "test review2", "test review3", "test review4"))
        );
    }

    private Review createReviewOfMemberIdAndContent(final Long memberId, final String content) {
        return Review.builder()
                .rating(5)
                .memberId(memberId)
                .placeId(1L)
                .content(content)
                .imageIds(List.of(1L, 2L, 3L))
                .tagMappings(new ArrayList<>())
                .build();
    }

}
