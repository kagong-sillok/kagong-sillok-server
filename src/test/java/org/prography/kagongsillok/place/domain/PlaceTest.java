package org.prography.kagongsillok.place.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlaceTest {

    @Test
    void 장소를_생성한다() {
        final Place place = Place.builder()
                .name("테스트 카페")
                .address("테스트특별시 테스트구 테스트로 1004")
                .location(Location.of(90.0, 123.123))
                .imageIds("1,2,3")
                .phone("010-1111-1111")
                .links(Links.of(List.of(
                        new Link(LinkType.INSTAGRAM, "testInstagramUrl"),
                        new Link(LinkType.BLOG, "testBlogUrl"),
                        new Link(LinkType.WEB, "testWebUrl")
                )))
                .businessHours(BusinessHours.of(List.of(
                        new BusinessHour(DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.TUESDAY, LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.THURSDAY, LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.FRIDAY, LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.SATURDAY, LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.SUNDAY, LocalTime.of(12, 0), LocalTime.of(23, 59))
                )))
                .build();

        assertAll(
                () -> assertThat(place.getName()).isEqualTo("테스트 카페"),
                () -> assertThat(place.getAddress()).isEqualTo("테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(place.getLocation()).isEqualTo(Location.of(90.0, 123.123)),
                () -> assertThat(place.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(place.getPhone()).isEqualTo("010-1111-1111"),
                () -> assertThat(place.getLinks().getValues()).extracting("linkType")
                        .containsAll(Arrays.asList(LinkType.values())),
                () -> assertThat(place.getLinks().getValues()).extracting("url")
                        .containsAll(List.of("testInstagramUrl", "testBlogUrl", "testWebUrl")),
                () -> assertThat(place.getBusinessHours().getValues()).extracting("dayOfWeek")
                        .containsAll(Arrays.asList(DayOfWeek.values()))
        );
    }
}