package org.prography.kagongsillok.place.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    private Double latitude;
    private Double longitude;

    public Location(final Double latitude, final Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
