package org.prography.kagongsillok.bookmark.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.bookmark.application.BookmarkService;
import org.prography.kagongsillok.bookmark.ui.dto.BookmarkToggleRequest;
import org.prography.kagongsillok.common.resolver.LoginMember;
import org.prography.kagongsillok.common.resolver.dto.LoginMemberDto;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkV1Controller {

    private final BookmarkService bookmarkService;

    @PostMapping("/toggle")
    public ResponseEntity<CommonResponse<Void>> toggle(
            @LoginMember final LoginMemberDto loginMemberDto,
            @RequestBody final BookmarkToggleRequest request
    ) {
        bookmarkService.toggle(request.getPlaceId(), loginMemberDto.getMemberId());
        return CommonResponse.success();
    }
}
