package org.prography.kagongsillok.record.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.image.application.exception.NotFoundImageException;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.record.application.dto.StudyRecordCreateCommand;
import org.prography.kagongsillok.record.application.dto.StudyRecordDto;
import org.prography.kagongsillok.record.application.exception.NotFoundStudyRecordException;
import org.prography.kagongsillok.record.domain.StudyRecord;
import org.prography.kagongsillok.record.domain.StudyRecordRepository;
import org.prography.kagongsillok.review.domain.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyRecordService {

    private final StudyRecordRepository studyRecordRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public StudyRecordDto createStudyRecord(final StudyRecordCreateCommand command) {
        final Long placeId = command.getPlaceId();
        final Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundPlaceException(placeId));

        checkExistImage(command.getImageIds());

        final StudyRecord savedStudyRecord = studyRecordRepository.save(command.toEntity(place.getName()));

        return StudyRecordDto.of(savedStudyRecord, getImages(savedStudyRecord));
    }

    public List<StudyRecordDto> getMemberStudyRecords(final Long memberId) {
        final List<StudyRecord> studyRecords = studyRecordRepository.findMemberRecordByMemberId(memberId);

        return getStudyRecordDtos(studyRecords);
    }

    public List<StudyRecordDto> getMemberStudyRecordsByYearMonth(final Long memberId, final int year,
            final int month) {
        final List<StudyRecord> studyRecords = studyRecordRepository.findMemberRecordByMemberIdAndYearMonth(
                memberId, year, month);

        return getStudyRecordDtos(studyRecords);
    }

    public List<PlaceDto> getMemberStudyPlaces(final Long memberId) {
        final List<StudyRecord> studyRecords = studyRecordRepository.findMemberRecordByMemberId(memberId);
        final List<Long> placeIds = studyRecords
                .stream()
                .map(StudyRecord::getPlaceId)
                .collect(Collectors.toList());

        final List<Place> studyPlaces = placeRepository.findByIdIn(placeIds);

        return CustomListUtils.mapTo(studyPlaces, PlaceDto::from);
    }

    @Transactional
    public void deleteStudyRecord(final Long id) {
        final StudyRecord studyRecord = studyRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundStudyRecordException(id));

        studyRecord.delete();
    }

    private void checkExistImage(final List<Long> imageIds) {
        if (imageRepository.isExistIdIn(imageIds)) {
            throw new NotFoundImageException(imageIds);
        }
    }

    private List<Image> getImages(final StudyRecord studyRecord) {
        return imageRepository.findByIdIn(studyRecord.getImageIds());
    }

    private List<StudyRecordDto> getStudyRecordDtos(final List<StudyRecord> studyRecords) {
        return studyRecords.stream()
                .map(studyRecord -> StudyRecordDto.of(studyRecord, getImages(studyRecord)))
                .collect(Collectors.toList());
    }
}
