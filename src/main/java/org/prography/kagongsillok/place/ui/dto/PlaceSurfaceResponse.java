package org.prography.kagongsillok.place.ui.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.place.application.dto.PlaceSurfaceDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceSurfaceResponse {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String thumbnailImageUrl;

    @Builder
    public PlaceSurfaceResponse(
            final Long id,
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final String phone,
            final String thumbnailImageUrl
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public static PlaceSurfaceResponse from(final PlaceSurfaceDto placeDto) {
        return PlaceSurfaceResponse.builder()
                .id(placeDto.getId())
                .name(placeDto.getName())
                .address(placeDto.getAddress())
                .latitude(placeDto.getLatitude())
                .longitude(placeDto.getLongitude())
                .phone(placeDto.getPhone())
                .thumbnailImageUrl(placeDto.getThumbnailImageUrl())
                .build();
    }
}
