package org.prography.kagongsillok.place.infrastructure;

import static org.prography.kagongsillok.place.domain.QPlace.place;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.member.domain.dto.PlaceSurfaceInfo;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private static final int DEFAULT_SEARCH_RESULT_SIZE = 500;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Place> findByLocationAround(
            final Location location,
            final Double latitudeBound,
            final Double longitudeBound
    ) {
        if (Objects.isNull(location)) {
            return List.of();
        }

        return queryFactory.selectFrom(place)
                .where(
                        latitudeBetween(location, latitudeBound),
                        longitudeBetween(location, longitudeBound),
                        isNotDeleted()
                )
                .limit(DEFAULT_SEARCH_RESULT_SIZE)
                .fetch();
    }

    @Override
    public List<PlaceSurfaceInfo> searchPlace(
            final String name,
            final Location location,
            final Double latitudeBound,
            final Double longitudeBound
    ) {
        if (Objects.isNull(name)) {
            return List.of();
        }

        return queryFactory
                .select(
                        Projections.constructor(
                                PlaceSurfaceInfo.class,
                                place.id,
                                place.name,
                                place.address,
                                place.location.latitude,
                                place.location.longitude,
                                place.phone,
                                place.thumbnailImageUrl
                        )
                )
                .from(place)
                .where(
                        latitudeBetween(location, latitudeBound),
                        longitudeBetween(location, longitudeBound),
                        nameContains(name),
                        isNotDeleted()
                )
                .limit(DEFAULT_SEARCH_RESULT_SIZE)
                .fetch();
    }

    @Override
    public List<Place> findByIdIn(final List<Long> placeIds) {
        return queryFactory
                .selectFrom(place)
                .where(
                        idIn(placeIds),
                        isNotDeleted()
                )
                .fetch();
    }

    @Override
    public Map<Long, Place> findByIdInToMap(final List<Long> placeIds) {
        return queryFactory
                .selectFrom(place)
                .where(
                        idIn(placeIds),
                        isNotDeleted()
                )
                .stream()
                .collect(Collectors.toMap(Place::getId, Function.identity()));
    }

    private BooleanExpression nameContains(final String name) {
        if (Objects.isNull(name)) {
            return null;
        }

        return place.name.contains(name);
    }

    private BooleanExpression latitudeBetween(final Location location, final Double latitudeBound) {
        if (Objects.isNull(latitudeBound)) {
            return null;
        }
        return place.location.latitude.between(
                location.getLatitude() - latitudeBound,
                location.getLatitude() + latitudeBound
        );
    }

    private BooleanExpression longitudeBetween(final Location location, final Double longitudeBound) {
        if (Objects.isNull(longitudeBound)) {
            return null;
        }
        return place.location.longitude.between(
                location.getLongitude() - longitudeBound,
                location.getLongitude() + longitudeBound
        );
    }

    private BooleanExpression idIn(final List<Long> ids) {
        return place.id.in(ids);
    }

    private BooleanExpression isNotDeleted() {
        return place.isDeleted.eq(Boolean.FALSE);
    }
}
