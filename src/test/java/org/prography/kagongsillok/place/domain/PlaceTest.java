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
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(List.of(
                        new Link(LinkType.INSTAGRAM.name(), "testInstagramUrl"),
                        new Link(LinkType.BLOG.name(), "testBlogUrl"),
                        new Link(LinkType.WEB.name(), "testWebUrl")
                ))
                .businessHours(List.of(
                        new BusinessHour(DayOfWeek.MONDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.TUESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.WEDNESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.THURSDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.FRIDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.SATURDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)),
                        new BusinessHour(DayOfWeek.SUNDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59))
                ))
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