package org.prography.kagongsillok.auth.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.KakaoAuthService;
import org.prography.kagongsillok.auth.application.LocalAuthService;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.ui.dto.KakaoJoinRequest;
import org.prography.kagongsillok.auth.ui.dto.KakaoLoginRequest;
import org.prography.kagongsillok.auth.ui.dto.LocalJoinRequest;
import org.prography.kagongsillok.auth.ui.dto.LocalLoginRequest;
import org.prography.kagongsillok.auth.ui.dto.LoginResultResponse;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.ui.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthV1Controller {

    private final LocalAuthService localAuthService;
    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/local/join")
    public ResponseEntity<CommonResponse<MemberResponse>> localJoin(@RequestBody final LocalJoinRequest request) {
        final MemberDto memberDto = localAuthService.localJoin(request.toCommand());
        return CommonResponse.success(MemberResponse.from(memberDto));
    }

    @PostMapping("/local/login")
    public ResponseEntity<CommonResponse<LoginResultResponse>> localLogin(
            @RequestBody final LocalLoginRequest request
    ) {
        final LoginResultDto loginResultDto = localAuthService.localLogin(request.getLoginId(), request.getPassword());
        return CommonResponse.success(LoginResultResponse.from(loginResultDto));
    }

    @PostMapping("/kakao/join")
    public ResponseEntity<CommonResponse<MemberResponse>> kakaoJoin(@RequestBody final KakaoJoinRequest request) {
        final MemberDto memberDto = kakaoAuthService.kakaoJoin(request.toCommand());
        return CommonResponse.success(MemberResponse.from(memberDto));
    }

    @PostMapping("/kakao/login")
    public ResponseEntity<CommonResponse<LoginResultResponse>> kakaoLogin(
            @RequestBody final KakaoLoginRequest request
    ) {
        final LoginResultDto loginResultDto = kakaoAuthService.kakaoLogin(
                request.getAuthorizationCode(),
                request.getRedirectUri()
        );
        return CommonResponse.success(LoginResultResponse.from(loginResultDto));
    }
}
