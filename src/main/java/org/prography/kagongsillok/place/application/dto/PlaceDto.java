package org.prography.kagongsillok.place.application.dto;

import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Place;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceDto {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Long> imageIds;
    private String phone;
    private List<LinkDto> links;
    private List<BusinessHourDto> businessHours;

    @Builder
    public PlaceDto(
            final Long id,
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final List<Long> imageIds,
            final String phone,
            final List<LinkDto> links,
            final List<BusinessHourDto> businessHours
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

    public static PlaceDto from(final Place place) {
        return PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .imageIds(place.getImageIds())
                .phone(place.getPhone())
                .links(CustomListUtils.mapTo(place.getLinks().getValues(), LinkDto::from))
                .businessHours(CustomListUtils.mapTo(place.getBusinessHours().getValues(), BusinessHourDto::from))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LinkDto {

        private Long id;
        private String linkType;
        private String url;

        private LinkDto(final Long id, final String linkType, final String url) {
            this.id = id;
            this.linkType = linkType;
            this.url = url;
        }

        public static LinkDto from(final Link link) {
            return new LinkDto(link.getId(), link.getLinkType().name(), link.getUrl());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BusinessHourDto {

        private Long id;
        private String dayOfWeek;
        private LocalTime open;
        private LocalTime close;

        private BusinessHourDto(final Long id, final String dayOfWeek, final LocalTime open, final LocalTime close) {
            this.id = id;
            this.dayOfWeek = dayOfWeek;
            this.open = open;
            this.close = close;
        }

        public static BusinessHourDto from(final BusinessHour businessHour) {
            return new BusinessHourDto(
                businessHour.getId(),
                businessHour.getDayOfWeek().name(),
                businessHour.getOpen(),
                businessHour.getClose()
            );
        }
    }
}
