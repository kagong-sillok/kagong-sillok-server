package org.prography.kagongsillok.member.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceSurfaceInfo {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String thumbnailImageUrl;

    public PlaceSurfaceInfo(
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
}
