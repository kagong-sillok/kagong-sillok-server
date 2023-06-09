package org.prography.kagongsillok.review.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.review.application.ReviewService;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewListResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewV1Controller {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<CommonResponse<ReviewResponse>> createReview(
            @RequestBody final ReviewCreateRequest reviewCreateRequest
    ) {
        final ReviewDto createdReview = reviewService.createReview(reviewCreateRequest.toCommand());
        return CommonResponse.success(ReviewResponse.from(createdReview));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ReviewResponse>> getReview(
            @PathVariable("id") Long id
    ) {
        final ReviewDto reviewDto = reviewService.getReview(id);
        return CommonResponse.success(ReviewResponse.from(reviewDto));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommonResponse<ReviewListResponse>> getAllReviews(
            @PathVariable("memberId") Long memberId
    ) {
        final List<ReviewDto> reviewDtos = reviewService.getAllReviewsByMemberId(memberId);
        return CommonResponse.success(ReviewListResponse.of(reviewDtos));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ReviewResponse>> updateReview(
            @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        final ReviewDto updatedReview = reviewService.updateReview(
                reviewUpdateRequest.getId(),
                reviewUpdateRequest.toCommand()
        );
        return CommonResponse.success(ReviewResponse.from(updatedReview));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") final Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}
