package org.prography.kagongsillok.member.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public MemberDto getMember(final Long id) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(id));
        if (member.getIsDeleted()) {
            throw new NotFoundMemberException(id);
        }

        List<StudyRecord> memberStudyRecords = studyRecordRepository.findMemberRecordByMemberId(id);

        return MemberDto.from(member, calculateTotalStudyTime(memberStudyRecords));
    }

    private int calculateTotalStudyTime(final List<StudyRecord> studyRecords) {
        return studyRecords.stream().mapToInt(studyRecord -> studyRecord.getDuration()).sum();
    }
}
