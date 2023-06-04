package org.prography.kagongsillok.place.application.dto;

import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Place;

public record PlaceCreateCommand(
        String name,
        String address,
        Double latitude,
        Double longitude,
        List<Long> imageIds,
        String phone,
        List<LinkCreateCommand> links,
        List<BusinessHourCreateCommand> businessHours
) {

    @Builder
    public PlaceCreateCommand {
    }

    public Place toEntity() {
        return Place.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(imageIds)
                .phone(phone)
                .links(CustomListUtils.mapTo(links, LinkCreateCommand::toEntity))
                .businessHours(CustomListUtils.mapTo(businessHours, BusinessHourCreateCommand::toEntity))
                .build();
    }

    public record LinkCreateCommand(String linkType, String url) {

        public Link toEntity() {
            return new Link(linkType, url);
        }
    }

    public record BusinessHourCreateCommand(String dayOfWeek, LocalTime open, LocalTime close) {

        public BusinessHour toEntity() {
            return new BusinessHour(dayOfWeek, open, close);
        }
    }
}
