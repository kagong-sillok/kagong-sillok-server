package org.prography.kagongsillok.place.infrastructure;

import static org.prography.kagongsillok.place.domain.QPlace.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private static final int DEFAULT_SEARCH_RESULT_SIZE = 50;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Place> findByLocationAround(
            final Location location,
            final Double latitudeBound,
            final Double longitudeBound
    ) {
        return queryFactory.selectFrom(place)
                .where(latitudeBetween(location, latitudeBound), longitudeBetween(location, longitudeBound))
                .limit(DEFAULT_SEARCH_RESULT_SIZE)
                .fetch();
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
}
