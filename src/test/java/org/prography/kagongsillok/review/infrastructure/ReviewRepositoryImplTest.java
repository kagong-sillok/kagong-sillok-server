package org.prography.kagongsillok.review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
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
                .imageUrls(List.of("image1", "image2", "image3"))
                .tags(List.of("#tag1", "#tag2", "#tag3"))
                .build();
        reviewRepository.save(reviewCreateCommand.toEntity());

        final Review savedReview = reviewRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException());

        assertAll(
                () -> assertThat(savedReview.getRating()).isEqualTo(3),
                () -> assertThat(savedReview.getMemberId()).isEqualTo(3L),
                () -> assertThat(savedReview.getContent()).isEqualTo("test review1"),
                () -> assertThat(savedReview.getImages().getImages()).containsAll(Set.of("image1", "image2", "image3")),
                () -> assertThat(savedReview.getTags().getTags()).containsAll(Set.of("#tag1", "#tag2", "#tag3"))
        );
    }


}
