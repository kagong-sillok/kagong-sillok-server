package org.prography.kagongsillok.tag.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.tag.application.TagService;
import org.prography.kagongsillok.tag.application.dto.TagDto;
import org.prography.kagongsillok.tag.ui.dto.TagCreateRequest;
import org.prography.kagongsillok.tag.ui.dto.TagListResponse;
import org.prography.kagongsillok.tag.ui.dto.TagResponse;
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
@RequestMapping("/api/v1/tags")
public class TagV1Controller {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<CommonResponse<TagResponse>> createTag(
            @RequestBody final TagCreateRequest tagCreateRequest
    ) {
        final TagDto createdTag = tagService.createTag(tagCreateRequest.toCommand());
        return CommonResponse.success(TagResponse.from(createdTag));
    }

    @GetMapping("/{ids}")
    public ResponseEntity<CommonResponse<TagListResponse>> getTags(
            @PathVariable("ids") final List<Long> ids
    ) {
        final List<TagDto> tagDtos = tagService.getTags(ids);
        return CommonResponse.success(TagListResponse.from(tagDtos));
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponse<TagListResponse>> getAllTags() {
        final List<TagDto> tagDtos = tagService.getAllTags();
        return CommonResponse.success(TagListResponse.from(tagDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") final Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
