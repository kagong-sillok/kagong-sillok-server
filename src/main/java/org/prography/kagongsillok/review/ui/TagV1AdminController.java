package org.prography.kagongsillok.review.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.review.application.ReviewTagService;
import org.prography.kagongsillok.review.application.dto.ReviewTagDto;
import org.prography.kagongsillok.review.ui.dto.ReviewTagCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewTagListResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewTagResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/tags")
public class TagV1AdminController {

    private final ReviewTagService reviewTagService;

    @PostMapping
    public ResponseEntity<CommonResponse<ReviewTagResponse>> createTag(
            @RequestBody final ReviewTagCreateRequest reviewTagCreateRequest
    ) {
        final ReviewTagDto createdTag = reviewTagService.createTag(reviewTagCreateRequest.toCommand());
        return CommonResponse.success(ReviewTagResponse.from(createdTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") final Long id) {
        reviewTagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
