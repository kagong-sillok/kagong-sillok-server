package org.prography.kagongsillok.auth.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.KakaoJoinCommand;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.KakaoAccountRepository;
import org.prography.kagongsillok.auth.domain.KakaoApiCaller;
import org.prography.kagongsillok.auth.domain.LoginManager;
import org.prography.kagongsillok.auth.domain.dto.KakaoUserResult;
import org.prography.kagongsillok.auth.domain.entity.KakaoAccount;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoAuthService {

    private final MemberRepository memberRepository;
    private final KakaoAccountRepository kakaoAccountRepository;
    private final KakaoApiCaller kakaoApiCaller;
    private final LoginManager loginManager;

    @Value("${oauth.kakao.clientId:localClientId}")
    private String kakaoOAuthClientId;

    @Value("${oauth.kakao.clientSecret:localClientSecret}")
    private String kakaoOAuthClientSecret;

    @Transactional
    public MemberDto kakaoJoin(final KakaoJoinCommand command) {
        final String kakaoAccessToken = getKakaoAccessToken(command.getAuthorizationCode(), command.getRedirectUri());
        final KakaoUserResult kakaoUser = kakaoApiCaller.getKakaoUser(kakaoAccessToken);
        final Member savedMember = saveKakaoMember(kakaoUser, Role.valueOf(command.getRole()));
        final KakaoAccount savedKakaoAccount = saveKakaoAccount(kakaoUser, savedMember);

        return MemberDto.from(savedMember);
    }

    private Member saveKakaoMember(final KakaoUserResult kakaoUser, final Role role) {
        final Member member = new Member(kakaoUser.getNickname(), kakaoUser.getEmail(), role);
        return memberRepository.save(member);
    }

    private KakaoAccount saveKakaoAccount(final KakaoUserResult kakaoUser, final Member savedMember) {
        final KakaoAccount kakaoAccount = new KakaoAccount(kakaoUser.getKakaoId(), savedMember);
        return kakaoAccountRepository.save(kakaoAccount);
    }

    @Transactional
    public LoginResultDto kakaoLogin(final String authorizationCode, final String redirectUri) {
        final String kakaoAccessToken = getKakaoAccessToken(authorizationCode, redirectUri);
        final KakaoUserResult kakaoUser = kakaoApiCaller.getKakaoUser(kakaoAccessToken);

        final KakaoAccount kakaoAccount = kakaoAccountRepository.findByKakaoId(kakaoUser.getKakaoId())
                .orElseGet(() -> {
                    final Member savedMember = saveKakaoMember(kakaoUser, Role.MEMBER);
                    return saveKakaoAccount(kakaoUser, savedMember);
                });
        final Member member = kakaoAccount.getMember();
        return loginManager.loginMember(member);
    }

    private String getKakaoAccessToken(final String authorizationCode, final String redirectUri) {
        return kakaoApiCaller.getAccessToken(
                authorizationCode,
                redirectUri,
                kakaoOAuthClientId,
                kakaoOAuthClientSecret
        );
    }
}
