package org.prography.kagongsillok.place.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Test
    void 장소를_생성한다() {
        final PlaceCreateCommand placeCreateCommand = PlaceCreateCommand.builder()
                .name("테스트 카페")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(List.of(
                        new LinkCreateCommand(LinkType.INSTAGRAM.name(), "testInstagramUrl"),
                        new LinkCreateCommand(LinkType.BLOG.name(), "testBlogUrl"),
                        new LinkCreateCommand(LinkType.WEB.name(), "testWebUrl")
                ))
                .businessHours(List.of(
                        new BusinessHourCreateCommand(
                                DayOfWeek.MONDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.TUESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.WEDNESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.THURSDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.FRIDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.SATURDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.SUNDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        )
                ))
                .build();

        final PlaceDto createdPlace = placeService.createPlace(placeCreateCommand);

        assertAll(
                () -> assertThat(createdPlace.name()).isEqualTo("테스트 카페"),
                () -> assertThat(createdPlace.address()).isEqualTo("테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(createdPlace.latitude()).isEqualTo(90.0),
                () -> assertThat(createdPlace.longitude()).isEqualTo(123.123),
                () -> assertThat(createdPlace.imageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(createdPlace.phone()).isEqualTo("010-1111-1111"),
                () -> assertThat(createdPlace.links()).extracting("linkType")
                        .containsAll(List.of(LinkType.INSTAGRAM.name(), LinkType.BLOG.name(), LinkType.WEB.name())),
                () -> assertThat(createdPlace.links()).extracting("url")
                        .containsAll(List.of("testInstagramUrl", "testBlogUrl", "testWebUrl")),
                () -> assertThat(createdPlace.businessHours()).extracting("dayOfWeek")
                        .containsAll(List.of(
                                DayOfWeek.MONDAY.name(),
                                DayOfWeek.TUESDAY.name(),
                                DayOfWeek.WEDNESDAY.name(),
                                DayOfWeek.THURSDAY.name(),
                                DayOfWeek.FRIDAY.name(),
                                DayOfWeek.SATURDAY.name(),
                                DayOfWeek.SUNDAY.name()
                        ))
        );
    }

    @Test
    void getPlace() {
    }

    @Test
    void updatePlace() {
    }

    @Test
    void deletePlace() {
    }
}
