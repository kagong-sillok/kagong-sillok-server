package org.prography.kagongsillok.place.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PlaceRepositoryImplTest {

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
    @Autowired
    private PlaceRepositoryImpl placeRepositoryImpl;
    @Autowired
    private PlaceRepository placeRepository;

    @Test
    void 장소가_존재하도록_범위를_설정하고_위도_경도_주변_장소를_검색한다() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand
                .builder()
                .name("테스트 장소1")
                .address("테스트특별시 테스트구")
                .latitude(49.67)
                .longitude(129.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand2 = PlaceCreateCommand
                .builder()
                .name("테스트 장소2")
                .address("테스트특별시 테스트구")
                .latitude(68.67)
                .longitude(116.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        placeRepository.save(placeCreateCommand1.toEntity());
        placeRepository.save(placeCreateCommand2.toEntity());
        Location location1 = Location.of(57.29, 123.25);

        List<Place> places1 = placeRepositoryImpl.findByLocationAround(location1, 16.35, 17.62);

        assertAll(
                () -> assertThat(places1.size()).isEqualTo(2),
                () -> assertThat(places1).extracting("name")
                        .containsAll(List.of("테스트 장소1", "테스트 장소2")),
                () -> assertThat(places1).extracting("latitude")
                        .containsAll(List.of(49.67, 68.67)),
                () -> assertThat(places1).extracting("longitude")
                        .containsAll(List.of(129.23, 116.23))
        );
    }

    @Test
    void 장소가_존재하지_않도록_범위를_설정하고_위도_경도_주변_장소를_검색한다() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand
                .builder()
                .name("테스트 장소1")
                .address("테스트특별시 테스트구")
                .latitude(49.67)
                .longitude(129.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand2 = PlaceCreateCommand
                .builder()
                .name("테스트 장소2")
                .address("테스트특별시 테스트구")
                .latitude(-49.67)
                .longitude(-129.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        placeRepository.save(placeCreateCommand1.toEntity());
        placeRepository.save(placeCreateCommand2.toEntity());
        Location location1 = Location.of(12.29, 24.95);

        List<Place> places1 = placeRepositoryImpl.findByLocationAround(location1, 16.35, 17.84);

        assertAll(
                () -> assertThat(places1.size()).isEqualTo(0)
        );
    }

    @Test
    void 정확한_장소_이름으로_장소를_검색한다() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand
                .builder()
                .name("테스트 장소1")
                .address("테스트특별시 테스트구")
                .latitude(49.67)
                .longitude(129.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand2 = PlaceCreateCommand
                .builder()
                .name("테스트 장소2")
                .address("테스트특별시 테스트구")
                .latitude(49.66)
                .longitude(129.22)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand3 = PlaceCreateCommand
                .builder()
                .name("테스트 장소3")
                .address("테스트특별시 테스트구")
                .latitude(16.67)
                .longitude(-70.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        placeRepository.save(placeCreateCommand1.toEntity());
        placeRepository.save(placeCreateCommand2.toEntity());
        placeRepository.save(placeCreateCommand3.toEntity());
        final String name1 = "테스트 장소2";

        List<Place> places1 = placeRepositoryImpl.searchPlace(
                name1,
                Location.of(49.66, 129.22),
                5.0,
                5.0
        );

        assertAll(
                () -> assertThat(places1.size()).isEqualTo(1),
                () -> assertThat(places1).extracting("name")
                        .containsAll(List.of("테스트 장소2"))
        );
    }

    @Test
    void 포함된_장소_이름으로_장소를_검색한다() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand
                .builder()
                .name("테스트 장소1")
                .address("테스트특별시 테스트구")
                .latitude(49.67)
                .longitude(129.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand2 = PlaceCreateCommand
                .builder()
                .name("테스트 장소2")
                .address("테스트특별시 테스트구")
                .latitude(49.66)
                .longitude(129.22)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        final PlaceCreateCommand placeCreateCommand3 = PlaceCreateCommand
                .builder()
                .name("테스트 장소3")
                .address("테스트특별시 테스트구")
                .latitude(16.67)
                .longitude(-70.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        placeRepository.save(placeCreateCommand1.toEntity());
        placeRepository.save(placeCreateCommand2.toEntity());
        placeRepository.save(placeCreateCommand3.toEntity());

        List<Place> places1 = placeRepositoryImpl.searchPlace(
                "테스트 장소",
                Location.of(49.66, 129.22),
                5.0,
                5.0
        );

        assertAll(
                () -> assertThat(places1.size()).isEqualTo(2),
                () -> assertThat(places1).extracting("name")
                        .containsAll(List.of("테스트 장소1", "테스트 장소2"))
        );
    }
}
