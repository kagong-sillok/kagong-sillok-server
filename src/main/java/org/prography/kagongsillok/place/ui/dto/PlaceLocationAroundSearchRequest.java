package org.prography.kagongsillok.place.ui.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.place.application.dto.PlaceLocationAroundSearchCondition;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceLocationAroundSearchRequest {

    private Double latitude;
    private Double longitude;
    private Double latitudeBound;
    private Double longitudeBound;

    @Builder
    public PlaceLocationAroundSearchRequest(
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

    public PlaceLocationAroundSearchCondition toSearchCondition() {
        return PlaceLocationAroundSearchCondition.builder()
                .latitude(latitude)
                .longitude(longitude)
                .latitudeBound(latitudeBound)
                .longitudeBound(longitudeBound)
                .build();
    }
}
