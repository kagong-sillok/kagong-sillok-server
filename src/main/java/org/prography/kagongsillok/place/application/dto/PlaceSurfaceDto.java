package org.prography.kagongsillok.place.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.domain.dto.PlaceSurfaceInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceSurfaceDto {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String thumbnailImageUrl;

    @Builder
    public PlaceSurfaceDto(
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

    public static PlaceSurfaceDto from(final PlaceSurfaceInfo placeInfo) {
        return PlaceSurfaceDto.builder()
                .id(placeInfo.getId())
                .name(placeInfo.getName())
                .address(placeInfo.getAddress())
                .latitude(placeInfo.getLatitude())
                .longitude(placeInfo.getLongitude())
                .phone(placeInfo.getPhone())
                .thumbnailImageUrl(placeInfo.getThumbnailImageUrl())
                .build();
    }
}
