package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
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
        reviewRepository.save(review1);

        final Review savedReview = reviewRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException());

        assertAll(
                () -> assertThat(savedReview.getRating()).isEqualTo(5),
                () -> assertThat(savedReview.getMemberId()).isEqualTo(1L),
                () -> assertThat(savedReview.getContent()).isEqualTo("test review"),
                () -> assertThat(savedReview.getImages().getImages()).containsAll(Set.of("image1", "image2")),
                () -> assertThat(savedReview.getTags().getTags()).containsAll(Set.of("#tag1", "#tag2"))
        );
    }

    private Review createReviewOfMemberIdAndContent(final Long memberId, final String content) {
        return Review.builder()
                .rating(5)
                .memberId(memberId)
                .content(content)
                .images(Images.of(Set.of("image1", "image2")))
                .tags(Tags.of(Set.of("#tag1", "#tag2")))
                .build();
    }

}
