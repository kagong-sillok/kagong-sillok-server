package org.prography.kagongsillok.place.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceDto createPlace(final PlaceCreateCommand placeCreateCommand) {
        final Place savedPlace = placeRepository.save(placeCreateCommand.toEntity());
        return PlaceDto.from(savedPlace);
    }

    public PlaceDto getPlace(final Long id) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));
        return PlaceDto.from(place);
    }
}
