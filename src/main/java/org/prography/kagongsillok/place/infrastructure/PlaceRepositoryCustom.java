package org.prography.kagongsillok.place.infrastructure;

import java.util.List;
import java.util.Map;
import org.prography.kagongsillok.member.domain.dto.PlaceSurfaceInfo;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;

public interface PlaceRepositoryCustom {

    List<Place> findByLocationAround(
            final Location location,
            final Double latitudeBound,
            final Double longitudeBound
    );

    List<PlaceSurfaceInfo> searchPlace(
            final String name,
            final Location location,
            final Double latitudeBound,
            final Double longitudeBound
    );

    List<Place> findByIdIn(final List<Long> placeIds);

    Map<Long, Place> findByIdInToMap(final List<Long> placeIds);
}
