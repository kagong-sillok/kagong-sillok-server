package org.prography.kagongsillok.place.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand.BusinessHourUpdateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand.LinkUpdateCommand;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
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

    private final List<LinkCreateCommand> linkCreateCommands = List.of(
            new LinkCreateCommand(LinkType.INSTAGRAM.name(), "testInstagramUrl"),
            new LinkCreateCommand(LinkType.BLOG.name(), "testBlogUrl"),
            new LinkCreateCommand(LinkType.WEB.name(), "testWebUrl")
    );

    private final List<BusinessHourCreateCommand> businessHourCreateCommands = List.of(
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
    );

    @Test
    void 장소를_생성한다() {
        final PlaceCreateCommand placeCreateCommand = PlaceCreateCommand.builder()
                .name("테스트 카페")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
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
    void 장소를_조회한다() {
        final PlaceCreateCommand placeCreateCommand = PlaceCreateCommand.builder()
                .name("테스트 카페")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final Long createdPlaceId = placeService.createPlace(placeCreateCommand).id();

        final PlaceDto place = placeService.getPlace(createdPlaceId);

        assertAll(
                () -> assertThat(place.name()).isEqualTo("테스트 카페"),
                () -> assertThat(place.address()).isEqualTo("테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(place.latitude()).isEqualTo(90.0),
                () -> assertThat(place.longitude()).isEqualTo(123.123),
                () -> assertThat(place.imageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(place.phone()).isEqualTo("010-1111-1111"),
                () -> assertThat(place.links()).extracting("linkType")
                        .containsAll(List.of(LinkType.INSTAGRAM.name(), LinkType.BLOG.name(), LinkType.WEB.name())),
                () -> assertThat(place.links()).extracting("url")
                        .containsAll(List.of("testInstagramUrl", "testBlogUrl", "testWebUrl")),
                () -> assertThat(place.businessHours()).extracting("dayOfWeek")
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
    void 장소를_수정한다() {
        final PlaceCreateCommand placeCreateCommand = PlaceCreateCommand.builder()
                .name("테스트 카페")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final Long placeId = placeService.createPlace(placeCreateCommand).id();
        final PlaceUpdateCommand placeUpdateCommand = PlaceUpdateCommand.builder()
                .name("바뀐 테스트 카페")
                .address("바뀐 테스트특별시 테스트구 테스트로 1004")
                .latitude(89.0)
                .longitude(123.12)
                .imageIds(List.of(4L, 5L, 6L))
                .phone("010-2222-2222")
                .links(List.of(
                        new LinkUpdateCommand(LinkType.INSTAGRAM.name(), "updateTestInstagramUrl"),
                        new LinkUpdateCommand(LinkType.BLOG.name(), "updateTestBlogUrl"),
                        new LinkUpdateCommand(LinkType.WEB.name(), "updateTestWebUrl")
                ))
                .businessHours(List.of(
                        new BusinessHourUpdateCommand(
                                DayOfWeek.MONDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourUpdateCommand(
                                DayOfWeek.TUESDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourUpdateCommand(
                                DayOfWeek.WEDNESDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourUpdateCommand(
                                DayOfWeek.THURSDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourUpdateCommand(
                                DayOfWeek.FRIDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourUpdateCommand(
                                DayOfWeek.SATURDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourUpdateCommand(
                                DayOfWeek.SUNDAY.name(), LocalTime.of(13, 0), LocalTime.of(23, 59)
                        )
                ))
                .build();

        final PlaceDto updatedPlace = placeService.updatePlace(placeId, placeUpdateCommand);

        assertAll(
                () -> assertThat(updatedPlace.name()).isEqualTo("바뀐 테스트 카페"),
                () -> assertThat(updatedPlace.address()).isEqualTo("바뀐 테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(updatedPlace.latitude()).isEqualTo(89.0),
                () -> assertThat(updatedPlace.longitude()).isEqualTo(123.12),
                () -> assertThat(updatedPlace.imageIds()).isEqualTo(List.of(4L, 5L, 6L)),
                () -> assertThat(updatedPlace.phone()).isEqualTo("010-2222-2222"),
                () -> assertThat(updatedPlace.links()).extracting("linkType")
                        .containsAll(List.of(LinkType.INSTAGRAM.name(), LinkType.BLOG.name(), LinkType.WEB.name())),
                () -> assertThat(updatedPlace.links()).extracting("url")
                        .containsAll(List.of("updateTestInstagramUrl", "updateTestBlogUrl", "updateTestWebUrl")),
                () -> assertThat(updatedPlace.businessHours()).extracting("dayOfWeek")
                        .containsAll(List.of(
                                DayOfWeek.MONDAY.name(),
                                DayOfWeek.TUESDAY.name(),
                                DayOfWeek.WEDNESDAY.name(),
                                DayOfWeek.THURSDAY.name(),
                                DayOfWeek.FRIDAY.name(),
                                DayOfWeek.SATURDAY.name(),
                                DayOfWeek.SUNDAY.name()
                        )),
                () -> assertThat(updatedPlace.businessHours()).extracting("open")
                        .containsAll(List.of(
                                LocalTime.of(13, 0),
                                LocalTime.of(13, 0),
                                LocalTime.of(13, 0),
                                LocalTime.of(13, 0),
                                LocalTime.of(13, 0),
                                LocalTime.of(13, 0),
                                LocalTime.of(13, 0)
                        ))
        );
    }

    @Test
    void 장소를_삭제한다() {
        final PlaceCreateCommand placeCreateCommand = PlaceCreateCommand.builder()
                .name("테스트 카페")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final Long placeId = placeService.createPlace(placeCreateCommand).id();

        placeService.deletePlace(placeId);

        assertThatThrownBy(() -> placeService.getPlace(placeId))
                .isInstanceOf(NotFoundPlaceException.class)
                .hasMessageContaining(String.valueOf(placeId));
    }
}
