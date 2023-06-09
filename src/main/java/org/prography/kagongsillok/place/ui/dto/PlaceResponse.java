package org.prography.kagongsillok.place.ui.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceDto.BusinessHourDto;
import org.prography.kagongsillok.place.application.dto.PlaceDto.LinkDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceResponse {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Long> imageIds;
    private String phone;
    private List<LinkResponse> links;
    private List<BusinessHourResponse> businessHours;

    @Builder
    public PlaceResponse(
            final Long id,
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final List<Long> imageIds,
            final String phone,
            final List<LinkResponse> links,
            final List<BusinessHourResponse> businessHours
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageIds = imageIds;
        this.phone = phone;
        this.links = links;
        this.businessHours = businessHours;
    }

    public static PlaceResponse from(final PlaceDto placeDto) {
        return PlaceResponse.builder()
                .id(placeDto.getId())
                .name(placeDto.getName())
                .address(placeDto.getAddress())
                .latitude(placeDto.getLatitude())
                .longitude(placeDto.getLongitude())
                .imageIds(placeDto.getImageIds())
                .phone(placeDto.getPhone())
                .links(CustomListUtils.mapTo(placeDto.getLinks(), LinkResponse::from))
                .businessHours(CustomListUtils.mapTo(placeDto.getBusinessHours(), BusinessHourResponse::from))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LinkResponse {

        private Long id;
        private String linkType;
        private String url;

        public static LinkResponse from(final LinkDto linkDto) {
            return new LinkResponse(linkDto.getId(), linkDto.getLinkType(), linkDto.getUrl());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BusinessHourResponse {

        private Long id;
        private String dayOfWeek;
        private String open;
        private String close;

        public static BusinessHourResponse from(final BusinessHourDto businessHourDto) {
            return new BusinessHourResponse(
                    businessHourDto.getId(),
                    businessHourDto.getDayOfWeek(),
                    businessHourDto.getOpen().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    businessHourDto.getClose().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
        }
    }
}
