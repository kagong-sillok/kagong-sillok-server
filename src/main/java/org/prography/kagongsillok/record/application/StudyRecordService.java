package org.prography.kagongsillok.record.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.record.application.dto.StudyRecordCreateCommand;
import org.prography.kagongsillok.record.application.dto.StudyRecordDto;
import org.prography.kagongsillok.record.domain.StudyRecord;
import org.prography.kagongsillok.record.domain.StudyRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyRecordService {

    private final StudyRecordRepository studyRecordRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public StudyRecordDto createStudyRecord(final StudyRecordCreateCommand studyRecordCreateCommand) {
        final Long placeId = studyRecordCreateCommand.getPlaceId();
        final Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundPlaceException(placeId));

        final StudyRecord savedStudyRecord = studyRecordRepository.save(studyRecordCreateCommand.toEntity(place.getName()));

        return StudyRecordDto.from(savedStudyRecord);
    }

    public List<StudyRecordDto> getMemberStudyRecords(final Long memberId) {
        final List<StudyRecord> studyRecords = studyRecordRepository.findMemberRecordByMemberId(memberId);

        return CustomListUtils.mapTo(studyRecords, StudyRecordDto::from);
    }
}
