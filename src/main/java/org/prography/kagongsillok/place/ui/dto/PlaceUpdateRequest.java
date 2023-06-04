package org.prography.kagongsillok.place.ui.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand.BusinessHourUpdateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand.LinkUpdateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceUpdateRequest {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Long> imageIds;
    private String phone;
    private List<LinkUpdateRequest> links;
    private List<BusinessHourUpdateRequest> businessHours;

    public PlaceUpdateCommand toCommand() {
        return PlaceUpdateCommand.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(imageIds)
                .phone(phone)
                .links(CustomListUtils.mapTo(links, LinkUpdateRequest::toCommand))
                .businessHours(CustomListUtils.mapTo(businessHours, BusinessHourUpdateRequest::toCommand))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LinkUpdateRequest {

        private String linkType;
        private String url;

        public LinkUpdateCommand toCommand() {
            return new LinkUpdateCommand(linkType, url);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BusinessHourUpdateRequest {

        private String dayOfWeek;
        private String open;
        private String close;

        public BusinessHourUpdateCommand toCommand() {
            return new BusinessHourUpdateCommand(
                    dayOfWeek,
                    LocalTime.parse(open, DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalTime.parse(close, DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
        }
    }
}
