package org.prography.kagongsillok.auth.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.AuthType;
import org.prography.kagongsillok.auth.domain.Authentication;
import org.prography.kagongsillok.auth.domain.AuthenticationRepository;
import org.prography.kagongsillok.auth.domain.KakaoAccountRepository;
import org.prography.kagongsillok.auth.domain.LocalAccount;
import org.prography.kagongsillok.auth.domain.LocalAccountRepository;
import org.prography.kagongsillok.auth.domain.PasswordEncryptor;
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
    private final AuthenticationRepository authenticationRepository;
    private final LocalAccountRepository localAccountRepository;
    private final KakaoAccountRepository kakaoAccountRepository;

    @Transactional
    public MemberDto localJoin(final LocalJoinCommand command) {
        final Member savedMember = saveMember(command);
        final Authentication savedAuthentication = saveAuthentication(savedMember);
        final LocalAccount savedLocalAccount = saveLocalAccount(command, savedAuthentication);

        return MemberDto.from(savedMember);
    }

    private Member saveMember(final LocalJoinCommand command) {
        final Member member = command.toMemberEntity();
        return memberRepository.save(member);
    }

    private Authentication saveAuthentication(final Member member) {
        final Authentication authentication = new Authentication(AuthType.LOCAL, member);
        return authenticationRepository.save(authentication);
    }

    private LocalAccount saveLocalAccount(final LocalJoinCommand command, final Authentication authentication) {
        final LocalAccount localAccount = new LocalAccount(
                command.getLoginId(),
                passwordEncryptor.encrypt(command.getPassword()),
                authentication
        );
        return localAccountRepository.save(localAccount);
    }

//    @Transactional
//    public LoginResultDto localLogin(final String loginId, final String planePassword) {
//
//    }
}
