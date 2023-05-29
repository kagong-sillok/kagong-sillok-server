package org.prography.kagongsillok.place.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.place.domain.exception.InvalidLocationBoundException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    public static final double ONE_KILOMETER_LATITUDE_BOUND = 0.0091;
    public static final double ONE_KILOMETER_LONGITUDE_BOUND = 0.0113;

    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;

    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;

    private Double latitude;
    private Double longitude;

    public Location(final Double latitude, final Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Location of(final Double latitude, final Double longitude) {
        validateLocation(latitude, longitude);
        return new Location(latitude, longitude);
    }

    private static void validateLocation(final Double latitude, final Double longitude) {
        if (isNotLatitudeBound(latitude)) {
            throw new InvalidLocationBoundException(latitude, longitude);
        }

        if (isNotLongitudeBound(longitude)) {
            throw new InvalidLocationBoundException(latitude, longitude);
        }
    }

    private static boolean isNotLatitudeBound(final Double latitude) {
        return latitude > MAX_LATITUDE || latitude < MIN_LATITUDE;
    }

    private static boolean isNotLongitudeBound(final Double longitude) {
        return longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Location location)) {
            return false;
        }
        return Objects.equals(getLatitude(), location.getLatitude()) && Objects.equals(getLongitude(),
                location.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude());
    }
}
