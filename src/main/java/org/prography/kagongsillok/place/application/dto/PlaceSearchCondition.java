package org.prography.kagongsillok.place.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceSearchCondition {

    private Double latitude;
    private Double longitude;
    private Double latitudeBound;
    private Double longitudeBound;
    private String name;

    @Builder
    public PlaceSearchCondition(
            final Double latitude,
            final Double longitude,
            final Double latitudeBound,
            final Double longitudeBound,
            final String name
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeBound = latitudeBound;
        this.longitudeBound = longitudeBound;
        this.name = name;
    }
}
