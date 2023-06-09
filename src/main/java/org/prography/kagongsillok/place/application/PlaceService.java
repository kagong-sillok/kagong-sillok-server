package org.prography.kagongsillok.place.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceLocationAroundSearchCondition;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Location;
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
        if (place.getIsDeleted()) {
            throw new NotFoundPlaceException(id);
        }

        return PlaceDto.from(place);
    }

    public List<PlaceDto> searchPlacesLocationAround(final PlaceLocationAroundSearchCondition searchCondition) {
        final List<Place> places = placeRepository.findByLocationAround(
                Location.of(searchCondition.getLatitude(), searchCondition.getLongitude()),
                searchCondition.getLatitudeBound(),
                searchCondition.getLongitudeBound()
        );

        return CustomListUtils.mapTo(places, PlaceDto::from);
    }

    public List<PlaceDto> searchPlacesByName(final String name) {
        final List<Place> places = placeRepository.findByNameContains(name);

        return CustomListUtils.mapTo(places, PlaceDto::from);
    }

    @Transactional
    public PlaceDto updatePlace(final Long id, final PlaceUpdateCommand updateCommand) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        final Place target = updateCommand.toEntity();
        place.update(target);

        return PlaceDto.from(place);
    }

    @Transactional
    public void deletePlace(final Long id) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        place.delete();
    }
}
