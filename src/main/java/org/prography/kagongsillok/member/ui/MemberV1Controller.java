package org.prography.kagongsillok.member.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.resolver.dto.MemberIdDto;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.member.application.MemberService;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.ui.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberV1Controller {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<CommonResponse<MemberResponse>> getMember(MemberIdDto memberIdDto) {
        final MemberDto memberDto = memberService.getMember(memberIdDto.getMemberId());
        return CommonResponse.success(MemberResponse.from(memberDto));
    }
}
