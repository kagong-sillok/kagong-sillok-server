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
public class PlaceUpdateCommand {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Long> imageIds;
    private String phone;
    private List<LinkUpdateCommand> links;
    private List<BusinessHourUpdateCommand> businessHours;

    @Builder
    public PlaceUpdateCommand(
            final Long id,
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final List<Long> imageIds,
            final String phone,
            final List<LinkUpdateCommand> links,
            final List<BusinessHourUpdateCommand> businessHours
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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LinkUpdateCommand {

        private String linkType;
        private String url;

        public LinkUpdateCommand(final String linkType, final String url) {
            this.linkType = linkType;
            this.url = url;
        }

        public Link toEntity() {
            return new Link(linkType, url);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BusinessHourUpdateCommand {

        private String dayOfWeek;
        private LocalTime open;
        private LocalTime close;

        public BusinessHourUpdateCommand(final String dayOfWeek, final LocalTime open, final LocalTime close) {
            this.dayOfWeek = dayOfWeek;
            this.open = open;
            this.close = close;
        }

        public BusinessHour toEntity() {
            return new BusinessHour(dayOfWeek, open, close);
        }
    }
}
