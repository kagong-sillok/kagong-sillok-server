package org.prography.kagongsillok.place.application.dto;

import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Place;

@Builder
@Getter
public record PlaceCreateDto(
        String name,
        String address,
        Double latitude,
        Double longitude,
        List<Long> imageIds,
        String phone,
        List<LinkCreateDto> links,
        List<BusinessHourCreateDto> businessHours
) {

    public Place toEntity() {
        return Place.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(imageIds)
                .phone(phone)
                .links(CustomListUtils.mapTo(links, LinkCreateDto::toEntity))
                .businessHours(CustomListUtils.mapTo(businessHours, BusinessHourCreateDto::toEntity))
                .build();
    }

    @Getter
    record LinkCreateDto(String linkType, String url) {

        public Link toEntity() {
            return new Link(linkType, url);
        }
    }

    @Getter
    record BusinessHourCreateDto(String dayOfWeek, LocalTime open, LocalTime close) {

        public BusinessHour toEntity() {
            return new BusinessHour(dayOfWeek, open, close);
        }
    }
}
