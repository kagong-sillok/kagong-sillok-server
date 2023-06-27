package org.prography.kagongsillok.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Test
    void 장소를_생성한다() {
        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
                .builder()
                .memberId(3L)
                .rating(5)
                .content("test review")
                .imageUrls(List.of("image1", "image2"))
                .tags(List.of("#tag1", "#tag2"))
                .build();

        final ReviewDto reviewDto = reviewService.createReview(reviewCreateCommand);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(3L),
                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
                () -> assertThat(reviewDto.getImageUrls()).containsAll(List.of("image1", "image2")),
                () -> assertThat(reviewDto.getTags()).containsAll(List.of("#tag1", "#tag2"))
        );
    }


}
