package org.prography.kagongsillok.auth.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.KakaoJoinCommand;
import org.prography.kagongsillok.auth.domain.KakaoAccountRepository;
import org.prography.kagongsillok.auth.domain.KakaoApiCaller;
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
public class KakaoMemberService {

    private final MemberRepository memberRepository;
    private final KakaoAccountRepository kakaoAccountRepository;
    private final KakaoApiCaller kakaoApiCaller;

    @Value("${oauth.kakao.clientId:localClientId}")
    private String kakaoOAuthClientId;

    @Value("${oauth.kakao.clientSecret:localClientSecret}")
    private String kakaoOAuthClientSecret;

    @Transactional
    public MemberDto kakaoJoin(final KakaoJoinCommand command) {
        final String kakaoAccessToken = getKakaoAccessToken(command);
        final KakaoUserResult kakaoUser = kakaoApiCaller.getKakaoUser(kakaoAccessToken);
        final Member savedMember = saveKakaoMember(kakaoUser, command.getRole());
        final KakaoAccount savedKakaoAccount = saveKakaoAccount(kakaoUser, savedMember);

        return MemberDto.from(savedMember);
    }

    private String getKakaoAccessToken(final KakaoJoinCommand command) {
        return kakaoApiCaller.getAccessToken(
                command.getAuthorizationCode(),
                command.getRedirectUri(),
                kakaoOAuthClientId,
                kakaoOAuthClientSecret
        );
    }

    private Member saveKakaoMember(final KakaoUserResult kakaoUser, final String role) {
        final Member member = new Member(kakaoUser.getNickname(), kakaoUser.getEmail(), Role.valueOf(role));
        return memberRepository.save(member);
    }

    private KakaoAccount saveKakaoAccount(final KakaoUserResult kakaoUser, final Member savedMember) {
        final KakaoAccount kakaoAccount = new KakaoAccount(kakaoUser.getKakaoId(), savedMember);
        return kakaoAccountRepository.save(kakaoAccount);
    }
}
