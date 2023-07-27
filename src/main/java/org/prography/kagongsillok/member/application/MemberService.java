package org.prography.kagongsillok.member.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.infrastructure.JwtAuthTokenProvider;
import org.prography.kagongsillok.member.application.dto.MemberDto;
import org.prography.kagongsillok.member.application.exception.NotFoundMemberException;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.record.domain.StudyRecord;
import org.prography.kagongsillok.record.domain.StudyRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final StudyRecordRepository studyRecordRepository;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public MemberDto getMember(final String accessToken) {
        Long memberId = jwtAuthTokenProvider.getLoginMemberByAccessToken(accessToken).getMemberId();
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        if (member.getIsDeleted()) {
            throw new NotFoundMemberException(memberId);
        }

        List<StudyRecord> memberStudyRecords = studyRecordRepository.findMemberRecordByMemberId(memberId);

        return MemberDto.from(member, calculateTotalStudyTime(memberStudyRecords));
    }

    private int calculateTotalStudyTime(final List<StudyRecord> studyRecords) {
        return studyRecords.stream().mapToInt(studyRecord -> studyRecord.getDuration()).sum();
    }
}
