package org.prography.kagongsillok.place.application.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.review.application.dto.ReviewTagDto;
import org.prography.kagongsillok.review.domain.ReviewTag;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceDto {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<ImageDto> images;
    private String phone;
    private Double ratingAverage;
    private List<LinkDto> links;
    private List<BusinessHourDto> businessHours;
    private List<ReviewTagDto> reviewTags;

    @Builder
    public PlaceDto(
            final Long id,
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final List<ImageDto> images,
            final Double ratingAverage,
            final String phone,
            final List<LinkDto> links,
            final List<BusinessHourDto> businessHours,
            final List<ReviewTagDto> reviewTags
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.ratingAverage = ratingAverage;
        this.phone = phone;
        this.links = links;
        this.businessHours = businessHours;
        this.reviewTags = reviewTags;
    }

    public static PlaceDto from(final Place place) {
        return PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .images(List.of())
                .reviewTags(List.of())
                .phone(place.getPhone())
                .links(CustomListUtils.mapTo(place.getLinks().getValues(), LinkDto::from))
                .businessHours(CustomListUtils.mapTo(place.getBusinessHours().getValues(), BusinessHourDto::from))
                .build();
    }

    public static PlaceDto of(final Place place, final List<Image> images) {
        return PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .images(CustomListUtils.mapTo(images, ImageDto::from))
                .reviewTags(List.of())
                .phone(place.getPhone())
                .links(CustomListUtils.mapTo(place.getLinks().getValues(), LinkDto::from))
                .businessHours(CustomListUtils.mapTo(place.getBusinessHours().getValues(), BusinessHourDto::from))
                .build();
    }

    public static PlaceDto of(
            final Place place,
            final List<Image> images,
            final List<ReviewTag> reviewTags,
            final Double ratingAvg
    ) {
        return PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .images(CustomListUtils.mapTo(images, ImageDto::from))
                .phone(place.getPhone())
                .ratingAverage(ratingAvg)
                .links(CustomListUtils.mapTo(place.getLinks().getValues(), LinkDto::from))
                .businessHours(CustomListUtils.mapTo(place.getBusinessHours().getValues(), BusinessHourDto::from))
                .reviewTags(CustomListUtils.mapTo(reviewTags, ReviewTagDto::from))
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
