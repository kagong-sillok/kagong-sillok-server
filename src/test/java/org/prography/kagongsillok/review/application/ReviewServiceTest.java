package org.prography.kagongsillok.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

//    @Test
//    void 리뷰를_생성한다() {
//        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(5)
//                .content("test review")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//
//        final ReviewDto reviewDto = reviewService.createReview(reviewCreateCommand);
//
//        assertAll(
//                () -> assertThat(reviewDto.getMemberId()).isEqualTo(3L),
//                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
//                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
//                () -> assertThat(reviewDto.getImageUrls()).containsAll(List.of("image1", "image2")),
//                () -> assertThat(reviewDto.getTags()).containsAll(List.of("#tag1", "#tag2"))
//        );
//    }
//
//    @Test
//    void 리뷰를_조회한다() {
//        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(5)
//                .content("test review")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();
//
//        final ReviewDto reviewDto = reviewService.getReview(createdReviewId);
//
//        assertAll(
//                () -> assertThat(reviewDto.getMemberId()).isEqualTo(3L),
//                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
//                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
//                () -> assertThat(reviewDto.getImageUrls()).containsAll(List.of("image1", "image2")),
//                () -> assertThat(reviewDto.getTags()).containsAll(List.of("#tag1", "#tag2"))
//        );
//    }
//
//    @Test
//    void 리뷰를_수정한다() {
//        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(5)
//                .content("test review")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();
//        final ReviewUpdateCommand reviewUpdateCommand = ReviewUpdateCommand
//                .builder()
//                .id(createdReviewId)
//                .memberId(3L)
//                .rating(4)
//                .content("updated test review")
//                .imageUrls(List.of("image1"))
//                .tags(List.of("#tag1"))
//                .build();
//
//        final ReviewDto reviewDto = reviewService.updateReview(reviewUpdateCommand);
//
//        assertAll(
//                () -> assertThat(reviewDto.getMemberId()).isEqualTo(3L),
//                () -> assertThat(reviewDto.getRating()).isEqualTo(4),
//                () -> assertThat(reviewDto.getContent()).isEqualTo("updated test review"),
//                () -> assertThat(reviewDto.getImageUrls()).containsAll(List.of("image1")),
//                () -> assertThat(reviewDto.getTags()).containsAll(List.of("#tag1"))
//        );
//    }
//
//    @Test
//    void 리뷰를_삭제한다() {
//        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(5)
//                .content("test review")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();
//        reviewService.deleteReview(createdReviewId);
//
//        assertThatThrownBy(() -> reviewService.getReview(createdReviewId))
//                .isInstanceOf(NotFoundReviewException.class)
//                .hasMessageContaining(String.valueOf(createdReviewId));
//    }
//
//    @Test
//    void 멤버_ID로_작성한_리뷰들을_조회한다() {
//        final ReviewCreateCommand reviewCreateCommand1 = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(5)
//                .content("test review1")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(1)
//                .content("test review2")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//        final ReviewCreateCommand reviewCreateCommand3 = ReviewCreateCommand
//                .builder()
//                .memberId(3L)
//                .rating(3)
//                .content("test review3")
//                .imageUrls(List.of("image1", "image2"))
//                .tags(List.of("#tag1", "#tag2"))
//                .build();
//        reviewService.createReview(reviewCreateCommand1);
//        reviewService.createReview(reviewCreateCommand2);
//        reviewService.createReview(reviewCreateCommand3);
//
//        final List<ReviewDto> reviewDtos = reviewService.getAllReviewsByMemberId(3L);
//
//        assertAll(
//                () -> assertThat(reviewDtos.size()).isEqualTo(3),
//                () -> assertThat(reviewDtos).extracting("rating")
//                        .containsAll(List.of(5, 1, 3)),
//                () -> assertThat(reviewDtos).extracting("content")
//                        .containsAll(List.of("test review1", "test review2", "test review3"))
//        );
//    }
}
