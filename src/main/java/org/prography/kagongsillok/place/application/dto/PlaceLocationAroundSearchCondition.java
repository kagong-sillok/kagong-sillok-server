package org.prography.kagongsillok.place.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceLocationAroundSearchCondition {

    private Double latitude;
    private Double longitude;
    private Double latitudeBound;
    private Double longitudeBound;

    @Builder
    public PlaceLocationAroundSearchCondition(
            final Double latitude,
            final Double longitude,
            final Double latitudeBound,
            final Double longitudeBound
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeBound = latitudeBound;
        this.longitudeBound = longitudeBound;
    }
}
