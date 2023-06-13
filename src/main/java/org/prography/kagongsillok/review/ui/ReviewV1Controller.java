package org.prography.kagongsillok.review.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.place.application.PlaceService;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;
import org.prography.kagongsillok.review.application.ReviewService;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

}
