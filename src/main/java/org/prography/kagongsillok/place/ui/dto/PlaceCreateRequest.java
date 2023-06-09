package org.prography.kagongsillok.place.ui.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceCreateRequest {

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Long> imageIds;
    private String phone;
    private List<LinkCreateRequest> links;
    private List<BusinessHourCreateRequest> businessHours;

    @Builder
    public PlaceCreateRequest(final String name, final String address, final Double latitude, final Double longitude,
            final List<Long> imageIds,
            final String phone, final List<LinkCreateRequest> links,
            final List<BusinessHourCreateRequest> businessHours) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageIds = imageIds;
        this.phone = phone;
        this.links = links;
        this.businessHours = businessHours;
    }

    public PlaceCreateCommand toCommand() {
        return PlaceCreateCommand.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(imageIds)
                .phone(phone)
                .links(CustomListUtils.mapTo(links, LinkCreateRequest::toCommand))
                .businessHours(CustomListUtils.mapTo(businessHours, BusinessHourCreateRequest::toCommand))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LinkCreateRequest {

        public LinkCreateRequest(final String linkType, final String url) {
            this.linkType = linkType;
            this.url = url;
        }

        private String linkType;
        private String url;

        public LinkCreateCommand toCommand() {
            return new LinkCreateCommand(linkType, url);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BusinessHourCreateRequest {

        public BusinessHourCreateRequest(final String dayOfWeek, final String open, final String close) {
            this.dayOfWeek = dayOfWeek;
            this.open = open;
            this.close = close;
        }

        private String dayOfWeek;
        private String open;
        private String close;

        public BusinessHourCreateCommand toCommand() {
            return new BusinessHourCreateCommand(
                    dayOfWeek,
                    LocalTime.parse(open, DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalTime.parse(close, DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
        }
    }
}
