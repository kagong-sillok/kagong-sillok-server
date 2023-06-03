package org.prography.kagongsillok.place.application.dto;

import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Place;

public record PlaceDto(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        List<Long> imageIds,
        String phone,
        List<LinkDto> links,
        List<BusinessHourDto> businessHours
) {

    @Builder
    public PlaceDto {
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

    record LinkDto(Long id, String linkType, String url) {

        public static LinkDto from(final Link link) {
            return new LinkDto(link.getId(), link.getLinkType().name(), link.getUrl());
        }
    }

    record BusinessHourDto(Long id, String dayOfWeek, LocalTime open, LocalTime close) {

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
