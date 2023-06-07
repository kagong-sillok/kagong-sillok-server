package org.prography.kagongsillok.place.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    void 위경도_주변_장소를_조회한다() {
        final Place place1 = createPlaceOfNameAndLocation("테스트 카페1", 50.0, 80.0);
        final Place place2 = createPlaceOfNameAndLocation("테스트 카페2", 50.5, 80.5);
        final Place place3 = createPlaceOfNameAndLocation("테스트 카페3", 51.2, 81.2);
        final Place place4 = createPlaceOfNameAndLocation("테스트 카페4", 49.5, 79.5);
        placeRepository.save(place1);
        placeRepository.save(place2);
        placeRepository.save(place3);
        placeRepository.save(place4);

        final List<Place> places = placeRepository.findByLocationAround(Location.of(50.0, 80.0), 0.6, 0.6);

        assertAll(
                () -> assertThat(places).hasSize(3),
                () -> assertThat(places).extracting("name")
                        .containsAll(List.of("테스트 카페1", "테스트 카페2", "테스트 카페4"))
        );
    }

    private Place createPlaceOfNameAndLocation(final String name, final Double latitude, final Double longitude) {
        return Place.builder()
                .name(name)
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(latitude)
                .longitude(longitude)
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
    }
}