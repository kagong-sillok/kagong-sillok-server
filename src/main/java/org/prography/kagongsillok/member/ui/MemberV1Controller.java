package org.prography.kagongsillok.member.ui;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.member.application.MemberService;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.ui.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberV1Controller {

    private final MemberService memberService;
    
    @GetMapping
    public ResponseEntity<CommonResponse<MemberResponse>> getMember(
            HttpServletRequest request
    ) {
        String accessToken = getAccessToken(request.getHeader("accessToken"));
        final MemberDto memberDto = memberService.getMember(accessToken);
        return CommonResponse.success(MemberResponse.from(memberDto));
    }

    private String getAccessToken(String accessTokenHeader) {
        return accessTokenHeader.trim().split(" ")[1];
    }
}
