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
import org.prography.kagongsillok.place.application.dto.PlaceLocationAroundSearchCondition;
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
                () -> assertThat(createdPlace.getName()).isEqualTo("테스트 카페"),
                () -> assertThat(createdPlace.getAddress()).isEqualTo("테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(createdPlace.getLatitude()).isEqualTo(90.0),
                () -> assertThat(createdPlace.getLongitude()).isEqualTo(123.123),
                () -> assertThat(createdPlace.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(createdPlace.getPhone()).isEqualTo("010-1111-1111"),
                () -> assertThat(createdPlace.getLinks()).extracting("linkType")
                        .containsAll(List.of(LinkType.INSTAGRAM.name(), LinkType.BLOG.name(), LinkType.WEB.name())),
                () -> assertThat(createdPlace.getLinks()).extracting("url")
                        .containsAll(List.of("testInstagramUrl", "testBlogUrl", "testWebUrl")),
                () -> assertThat(createdPlace.getBusinessHours()).extracting("dayOfWeek")
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
        final Long createdPlaceId = placeService.createPlace(placeCreateCommand).getId();

        final PlaceDto place = placeService.getPlace(createdPlaceId);

        assertAll(
                () -> assertThat(place.getName()).isEqualTo("테스트 카페"),
                () -> assertThat(place.getAddress()).isEqualTo("테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(place.getLatitude()).isEqualTo(90.0),
                () -> assertThat(place.getLongitude()).isEqualTo(123.123),
                () -> assertThat(place.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(place.getPhone()).isEqualTo("010-1111-1111"),
                () -> assertThat(place.getLinks()).extracting("linkType")
                        .containsAll(List.of(LinkType.INSTAGRAM.name(), LinkType.BLOG.name(), LinkType.WEB.name())),
                () -> assertThat(place.getLinks()).extracting("url")
                        .containsAll(List.of("testInstagramUrl", "testBlogUrl", "testWebUrl")),
                () -> assertThat(place.getBusinessHours()).extracting("dayOfWeek")
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
        final Long placeId = placeService.createPlace(placeCreateCommand).getId();
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
                () -> assertThat(updatedPlace.getName()).isEqualTo("바뀐 테스트 카페"),
                () -> assertThat(updatedPlace.getAddress()).isEqualTo("바뀐 테스트특별시 테스트구 테스트로 1004"),
                () -> assertThat(updatedPlace.getLatitude()).isEqualTo(89.0),
                () -> assertThat(updatedPlace.getLongitude()).isEqualTo(123.12),
                () -> assertThat(updatedPlace.getImageIds()).isEqualTo(List.of(4L, 5L, 6L)),
                () -> assertThat(updatedPlace.getPhone()).isEqualTo("010-2222-2222"),
                () -> assertThat(updatedPlace.getLinks()).extracting("linkType")
                        .containsAll(List.of(LinkType.INSTAGRAM.name(), LinkType.BLOG.name(), LinkType.WEB.name())),
                () -> assertThat(updatedPlace.getLinks()).extracting("url")
                        .containsAll(List.of("updateTestInstagramUrl", "updateTestBlogUrl", "updateTestWebUrl")),
                () -> assertThat(updatedPlace.getBusinessHours()).extracting("dayOfWeek")
                        .containsAll(List.of(
                                DayOfWeek.MONDAY.name(),
                                DayOfWeek.TUESDAY.name(),
                                DayOfWeek.WEDNESDAY.name(),
                                DayOfWeek.THURSDAY.name(),
                                DayOfWeek.FRIDAY.name(),
                                DayOfWeek.SATURDAY.name(),
                                DayOfWeek.SUNDAY.name()
                        )),
                () -> assertThat(updatedPlace.getBusinessHours()).extracting("open")
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
        final Long placeId = placeService.createPlace(placeCreateCommand).getId();

        placeService.deletePlace(placeId);

        assertThatThrownBy(() -> placeService.getPlace(placeId))
                .isInstanceOf(NotFoundPlaceException.class)
                .hasMessageContaining(String.valueOf(placeId));
    }

    @Test
    void 위도_경도로_장소를_검색한다() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand.builder()
                .name("테스트 카페1")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(120.129)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand2 = PlaceCreateCommand.builder()
                .name("테스트 카페2")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(-80.29)
                .longitude(-60.298)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand3 = PlaceCreateCommand.builder()
                .name("테스트 카페3")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(-88.29)
                .longitude(-67.298)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        placeService.createPlace(placeCreateCommand1);
        placeService.createPlace(placeCreateCommand2);
        placeService.createPlace(placeCreateCommand3);
        final PlaceLocationAroundSearchCondition placeLocationAroundSearchCondition1
                = PlaceLocationAroundSearchCondition.builder()
                .latitude(70.0)
                .longitude(110.80)
                .latitudeBound(25.0)
                .longitudeBound(19.70)
                .build();
        final PlaceLocationAroundSearchCondition placeLocationAroundSearchCondition2
                = PlaceLocationAroundSearchCondition.builder()
                .latitude(-50.28)
                .longitude(-80.830)
                .latitudeBound(42.152)
                .longitudeBound(50.26)
                .build();
        final PlaceLocationAroundSearchCondition placeLocationAroundSearchCondition3
                = PlaceLocationAroundSearchCondition.builder()
                .latitude(0.0)
                .longitude(0.283)
                .latitudeBound(5.012)
                .longitudeBound(10.152)
                .build();

        List<PlaceDto> searchPlaces1 = placeService.searchPlacesLocationAround(placeLocationAroundSearchCondition1);
        List<PlaceDto> searchPlaces2 = placeService.searchPlacesLocationAround(placeLocationAroundSearchCondition2);
        List<PlaceDto> searchPlaces3 = placeService.searchPlacesLocationAround(placeLocationAroundSearchCondition3);

        assertAll(
                ()-> assertThat(searchPlaces1.size()).isEqualTo(1),
                ()-> assertThat(searchPlaces1).extracting("name")
                        .containsAll(List.of("테스트 카페1")),
                () -> assertThat(searchPlaces2.size()).isEqualTo(2),
                () -> assertThat(searchPlaces2).extracting("name")
                        .containsAll(List.of("테스트 카페2", "테스트 카페3")),
                () -> assertThat(searchPlaces3.size()).isEqualTo(0)
        );
    }

    @Test
    void 장소_이름으로_장소를_검색한다() {
        final String name1 = "테스트 카페1";
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand.builder()
                .name("테스트 카페1")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(120.129)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand2 = PlaceCreateCommand.builder()
                .name("테스트 카페1")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(-80.29)
                .longitude(-60.298)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand3 = PlaceCreateCommand.builder()
                .name("테스트 카페3")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(-88.29)
                .longitude(-67.298)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("010-1111-1111")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        placeService.createPlace(placeCreateCommand1);
        placeService.createPlace(placeCreateCommand2);
        placeService.createPlace(placeCreateCommand3);

        List<PlaceDto> searchPlaces1 = placeService.searchPlacesByName(name1);

        assertAll(
                ()-> assertThat(searchPlaces1.size()).isEqualTo(2),
                ()-> assertThat(searchPlaces1).extracting("latitude")
                        .containsAll(List.of(90.0, -80.29)),
                ()-> assertThat(searchPlaces1).extracting("longitude")
                        .containsAll(List.of(120.129, -60.298))
        );
    }


}
