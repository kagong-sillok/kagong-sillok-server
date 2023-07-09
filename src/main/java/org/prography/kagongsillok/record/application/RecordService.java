package org.prography.kagongsillok.record.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.record.application.dto.RecordCreateCommand;
import org.prography.kagongsillok.record.application.dto.RecordDto;
import org.prography.kagongsillok.record.domain.Record;
import org.prography.kagongsillok.record.domain.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public RecordDto createRecord(final RecordCreateCommand recordCreateCommand) {
        final Long placeId = recordCreateCommand.getPlaceId();
        final Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundPlaceException(placeId));

        final Record savedRecord = recordRepository.save(recordCreateCommand.toEntity());

        return RecordDto.from(savedRecord, place.getName());
    }

}
