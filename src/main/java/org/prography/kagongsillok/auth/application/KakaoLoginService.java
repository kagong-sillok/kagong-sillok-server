package org.prography.kagongsillok.auth.application;

import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.KakaoAccountRepository;
import org.prography.kagongsillok.auth.domain.KakaoApiCaller;
import org.prography.kagongsillok.auth.domain.LoginManager;
import org.prography.kagongsillok.auth.domain.dto.KakaoUserInfo;
import org.prography.kagongsillok.auth.domain.entity.KakaoAccount;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class KakaoLoginService {

    private final MemberRepository memberRepository;
    private final KakaoAccountRepository kakaoAccountRepository;
    private final KakaoApiCaller kakaoApiCaller;
    private final LoginManager loginManager;

    private final String kakaoOAuthClientId;
    private final String kakaoOAuthClientSecret;

    public KakaoLoginService(
            final MemberRepository memberRepository,
            final KakaoAccountRepository kakaoAccountRepository,
            final KakaoApiCaller kakaoApiCaller,
            final LoginManager loginManager,
            @Value("${oauth.kakao.clientId:localClientId}") final String kakaoOAuthClientId,
            @Value("${oauth.kakao.clientSecret:localClientSecret}") final String kakaoOAuthClientSecret
    ) {
        this.memberRepository = memberRepository;
        this.kakaoAccountRepository = kakaoAccountRepository;
        this.kakaoApiCaller = kakaoApiCaller;
        this.loginManager = loginManager;
        this.kakaoOAuthClientId = kakaoOAuthClientId;
        this.kakaoOAuthClientSecret = kakaoOAuthClientSecret;
    }

    @Transactional
    public LoginResultDto login(final String authorizationCode, final String redirectUri) {
        final String kakaoAccessToken = getKakaoAccessToken(authorizationCode, redirectUri);
        final KakaoUserInfo kakaoUser = kakaoApiCaller.getKakaoUser(kakaoAccessToken);

        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(kakaoUser.getKakaoId())
                .orElseGet(() -> {
                    final Member savedMember = saveKakaoMember(kakaoUser, Role.MEMBER);
                    return saveKakaoAccount(kakaoUser, savedMember);
                });
        return loginManager.loginMember(kakaoAccount.getMember());
    }

    private String getKakaoAccessToken(final String authorizationCode, final String redirectUri) {
        return kakaoApiCaller.getAccessToken(
                authorizationCode,
                redirectUri,
                kakaoOAuthClientId,
                kakaoOAuthClientSecret
        );
    }

    private Member saveKakaoMember(final KakaoUserInfo kakaoUser, final Role role) {
        final Member member = new Member(kakaoUser.getNickname(), kakaoUser.getEmail(), role);
        return memberRepository.save(member);
    }

    private KakaoAccount saveKakaoAccount(final KakaoUserInfo kakaoUser, final Member savedMember) {
        final KakaoAccount kakaoAccount = new KakaoAccount(kakaoUser.getKakaoId(), savedMember);
        return kakaoAccountRepository.save(kakaoAccount);
    }
}
