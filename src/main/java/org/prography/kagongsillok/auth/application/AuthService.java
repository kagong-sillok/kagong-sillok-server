package org.prography.kagongsillok.auth.application;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.application.exception.AuthenticationException;
import org.prography.kagongsillok.auth.application.exception.NotFoundLocalAccountException;
import org.prography.kagongsillok.auth.domain.AccessTokenManager;
import org.prography.kagongsillok.auth.domain.KakaoAccountRepository;
import org.prography.kagongsillok.auth.domain.LocalAccount;
import org.prography.kagongsillok.auth.domain.LocalAccountRepository;
import org.prography.kagongsillok.auth.domain.PasswordEncryptor;
import org.prography.kagongsillok.auth.domain.RefreshToken;
import org.prography.kagongsillok.auth.domain.RefreshTokenManager;
import org.prography.kagongsillok.auth.domain.dto.AccessTokenCreateResult;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final PasswordEncryptor passwordEncryptor;
    private final MemberRepository memberRepository;
    private final LocalAccountRepository localAccountRepository;
    private final KakaoAccountRepository kakaoAccountRepository;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;

    @Transactional
    public MemberDto localJoin(final LocalJoinCommand command) {
        final Member savedMember = saveMember(command);
        final LocalAccount savedLocalAccount = saveLocalAccount(command, savedMember);

        return MemberDto.from(savedMember);
    }

    private Member saveMember(final LocalJoinCommand command) {
        final Member member = command.toMemberEntity();
        return memberRepository.save(member);
    }

    private LocalAccount saveLocalAccount(final LocalJoinCommand command, final Member member) {
        final LocalAccount localAccount = new LocalAccount(
                command.getLoginId(),
                passwordEncryptor.encrypt(command.getPassword()),
                member
        );
        return localAccountRepository.save(localAccount);
    }

    @Transactional
    public LoginResultDto localLogin(final String loginId, final String planePassword) {
        final LocalAccount localAccount = localAccountRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NotFoundLocalAccountException(loginId));

        matchPassword(planePassword, localAccount);

        final Member member = localAccount.getMember();
        return loginMember(member);
    }

    private void matchPassword(final String planePassword, final LocalAccount localAccount) {
        if (!passwordEncryptor.matches(planePassword, localAccount.getEncryptedPassword())) {
            throw new AuthenticationException("인증에 실패했습니다.");
        }
    }

    private LoginResultDto loginMember(final Member member) {
        final AccessTokenCreateResult accessTokenCreateResult = accessTokenManager.create(member);
        final RefreshToken refreshToken = refreshTokenManager.create(member.getId());

        return LoginResultDto.builder()
                .accessToken(accessTokenCreateResult.getAccessToken())
                .refreshToken(refreshToken.getValue())
                .accessTokenExpireDateTime(accessTokenCreateResult.getExpire())
                .refreshTokenExpireDateTime(refreshToken.getExpire())
                .build();
    }
}
