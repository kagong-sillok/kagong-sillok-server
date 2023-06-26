package org.prography.kagongsillok.auth.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.application.exception.AuthenticationException;
import org.prography.kagongsillok.auth.application.exception.NotFoundLocalAccountException;
import org.prography.kagongsillok.auth.domain.LocalAccountRepository;
import org.prography.kagongsillok.auth.domain.LoginManager;
import org.prography.kagongsillok.auth.domain.PasswordEncryptor;
import org.prography.kagongsillok.auth.domain.entity.LocalAccount;
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
    private final LoginManager loginManager;

    @Transactional
    public MemberDto localJoin(final LocalJoinCommand command) {
        final Member savedMember = memberRepository.save(command.toMemberEntity());
        final LocalAccount savedLocalAccount = saveLocalAccount(command, savedMember);

        return MemberDto.from(savedMember);
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
        return loginManager.loginMember(member);
    }

    private void matchPassword(final String planePassword, final LocalAccount localAccount) {
        if (!passwordEncryptor.matches(planePassword, localAccount.getEncryptedPassword())) {
            throw new AuthenticationException("인증에 실패했습니다.");
        }
    }

    @Transactional
    public LoginResultDto refresh(final String refreshTokenValue) {
        return loginManager.refresh(refreshTokenValue);
    }
}
