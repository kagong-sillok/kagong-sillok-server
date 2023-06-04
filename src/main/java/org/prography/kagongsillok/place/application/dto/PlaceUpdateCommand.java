package org.prography.kagongsillok.place.application.dto;

import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Place;

public record PlaceUpdateCommand(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        List<Long> imageIds,
        String phone,
        List<LinkUpdateCommand> links,
        List<BusinessHourUpdateCommand> businessHours
) {

    @Builder
    public PlaceUpdateCommand {
    }

    public Place toEntity() {
        return Place.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(imageIds)
                .phone(phone)
                .links(CustomListUtils.mapTo(links, LinkUpdateCommand::toEntity))
                .businessHours(CustomListUtils.mapTo(businessHours, BusinessHourUpdateCommand::toEntity))
                .build();
    }

    public record LinkUpdateCommand(String linkType, String url) {

        public Link toEntity() {
            return new Link(linkType, url);
        }
    }

    public record BusinessHourUpdateCommand(String dayOfWeek, LocalTime open, LocalTime close) {

        public BusinessHour toEntity() {
            return new BusinessHour(dayOfWeek, open, close);
        }
    }
}
