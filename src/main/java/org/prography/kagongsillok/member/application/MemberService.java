package org.prography.kagongsillok.member.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.resolver.dto.LoginMemberDto;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.application.exception.NotFoundMemberException;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto getMember(final LoginMemberDto loginMemberDto) {
        final Long memberId = loginMemberDto.getMemberId();
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        if (member.getIsDeleted()) {
            throw new NotFoundMemberException(memberId);
        }

        return MemberDto.from(member);
    }
}
