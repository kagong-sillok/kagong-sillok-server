package org.prography.kagongsillok.studyrecord.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.record.application.StudyRecordService;
import org.prography.kagongsillok.record.application.dto.StudyRecordCreateCommand;
import org.prography.kagongsillok.record.application.dto.StudyRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudyRecordServiceTest {

    @Autowired
    private StudyRecordService studyRecordService;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void 공부_기록을_생성한다() {
        final Long memberId = 1L;
        final String placeName = "place1";
        final Long imageId = saveImageAndGetImageId("imageUrl1");
        final Long placeId = createPlaceAndGetPlaceId(placeName, imageId);
        final StudyRecordCreateCommand studyRecordCreateCommand = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(10)
                .duration(100)
                .description("모각코")
                .imageIds(List.of(imageId))
                .build();

        final StudyRecordDto studyRecordDto = studyRecordService.createStudyRecord(studyRecordCreateCommand);

        assertAll(
                () -> assertThat(studyRecordDto.getPlaceName()).isEqualTo(placeName),
                () -> assertThat(studyRecordDto.getStudyDate()).isEqualTo(LocalDate.of(2023, 7, 10)),
                () -> assertThat(studyRecordDto.getDuration()).isEqualTo(100),
                () -> assertThat(studyRecordDto.getDescription()).isEqualTo("모각코"),
                () -> assertThat(studyRecordDto.getImages()).extracting("id")
                        .isEqualTo(List.of(imageId))
        );
    }

    @Test
    void 멤버_ID로_공부_기록들을_조회한다() {
        final Long memberId = 1L;
        final Long imageId = saveImageAndGetImageId("imageUrl1");
        final Long placeId1 = createPlaceAndGetPlaceId("place1", imageId);
        final Long placeId2 = createPlaceAndGetPlaceId("place2", imageId);
        final StudyRecordCreateCommand studyRecordCreateCommand1 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId1)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(10)
                .duration(100)
                .description("모각코")
                .imageIds(List.of(imageId))
                .build();
        final StudyRecordCreateCommand studyRecordCreateCommand2 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId2)
                .studyYear(2023)
                .studyMonth(5)
                .studyDay(15)
                .duration(120)
                .description("모각코")
                .imageIds(List.of(imageId))
                .build();
        studyRecordService.createStudyRecord(studyRecordCreateCommand1);
        studyRecordService.createStudyRecord(studyRecordCreateCommand2);

        final List<StudyRecordDto> studyRecordDtos = studyRecordService.getMemberStudyRecords(memberId);

        assertAll(
                () -> assertThat(studyRecordDtos.size()).isEqualTo(2),
                () -> assertThat(studyRecordDtos).extracting("placeName")
                        .containsAll(List.of("place1", "place2")),
                () -> assertThat(studyRecordDtos).extracting("studyDate")
                        .containsAll(List.of(LocalDate.of(2023, 7, 10), LocalDate.of(2023, 5, 15))),
                () -> assertThat(studyRecordDtos).extracting("duration")
                        .containsAll(List.of(100, 120))
        );
    }

    @Test
    void 멤버_ID와_년도와_월로_공부_기록들을_조회한다() {
        final Long memberId = 1L;
        final Long imageId = saveImageAndGetImageId("imageUrl1");
        final Long placeId1 = createPlaceAndGetPlaceId("place1", imageId);
        final Long placeId2 = createPlaceAndGetPlaceId("place2", imageId);
        final StudyRecordCreateCommand studyRecordCreateCommand1 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId1)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(10)
                .duration(30)
                .description("모각코1")
                .imageIds(List.of(imageId))
                .build();
        final StudyRecordCreateCommand studyRecordCreateCommand2 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId2)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(27)
                .duration(150)
                .description("모각코2")
                .imageIds(List.of(imageId))
                .build();
        studyRecordService.createStudyRecord(studyRecordCreateCommand1);
        studyRecordService.createStudyRecord(studyRecordCreateCommand2);

        final List<StudyRecordDto> studyRecordDtos = studyRecordService.getMemberStudyRecordsByYearMonth(memberId,
                2023, 7);

        assertAll(
                () -> assertThat(studyRecordDtos.size()).isEqualTo(2),
                () -> assertThat(studyRecordDtos).extracting("placeName")
                        .containsAll(List.of("place1", "place2")),
                () -> assertThat(studyRecordDtos).extracting("studyDate")
                        .containsAll(List.of(LocalDate.of(2023, 7, 10), LocalDate.of(2023, 7, 27))),
                () -> assertThat(studyRecordDtos).extracting("duration")
                        .containsAll(List.of(30, 150)),
                () -> assertThat(studyRecordDtos).extracting("description")
                        .containsAll(List.of("모각코1", "모각코2"))
        );
    }

    @Test
    void 멤버_ID로_공부_기록의_장소들을_조회한다() {
        final Long memberId = 1L;
        final Long imageId = saveImageAndGetImageId("imageUrl1");
        final Long placeId1 = createPlaceAndGetPlaceId("place1", imageId);
        final Long placeId2 = createPlaceAndGetPlaceId("place2", imageId);
        final Long placeId3 = createPlaceAndGetPlaceId("place3", imageId);
        final StudyRecordCreateCommand studyRecordCreateCommand1 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId1)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(10)
                .duration(111)
                .description("모각코1")
                .imageIds(List.of(imageId))
                .build();
        final StudyRecordCreateCommand studyRecordCreateCommand2 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId2)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(27)
                .duration(195)
                .description("모각코2")
                .imageIds(List.of(imageId))
                .build();
        final StudyRecordCreateCommand studyRecordCreateCommand3 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId3)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(27)
                .duration(200)
                .description("모각코3")
                .imageIds(List.of(imageId))
                .build();
        studyRecordService.createStudyRecord(studyRecordCreateCommand1);
        studyRecordService.createStudyRecord(studyRecordCreateCommand2);
        studyRecordService.createStudyRecord(studyRecordCreateCommand3);

        final List<PlaceDto> placeDtos = studyRecordService.getMemberStudyPlaces(memberId);

        assertAll(
                () -> assertThat(placeDtos.size()).isEqualTo(3),
                () -> assertThat(placeDtos).extracting("name")
                        .containsAll(List.of("place1", "place2", "place3")),
                () -> assertThat(placeDtos).extracting("latitude")
                        .containsAll(List.of(90.0, 90.0, 90.0)),
                () -> assertThat(placeDtos).extracting("longitude")
                        .containsAll(List.of(123.123, 123.123, 123.123))
        );
    }

    @Test
    void 공부_기록을_삭제한다() {
        final Long memberId = 1L;
        final Long imageId = saveImageAndGetImageId("imageUrl1");
        final Long placeId1 = createPlaceAndGetPlaceId("place1", imageId);
        final StudyRecordCreateCommand studyRecordCreateCommand1 = StudyRecordCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId1)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(10)
                .duration(123)
                .description("모각코1")
                .imageIds(List.of(imageId))
                .build();
        final Long createdStudyRecordId = studyRecordService.createStudyRecord(studyRecordCreateCommand1).getId();

        studyRecordService.deleteStudyRecord(createdStudyRecordId);
        final List<StudyRecordDto> studyRecordDtos = studyRecordService.getMemberStudyRecords(memberId);

        assertAll(
                () -> assertThat(studyRecordDtos.size()).isEqualTo(0)
        );
    }

    private Long createPlaceAndGetPlaceId(final String placeName, final Long imageId) {
        final Place place = Place.builder()
                .name(placeName)
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(123.123)
                .imageIds(List.of(imageId))
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

        return placeRepository.save(place).getId();
    }

    private Long saveImageAndGetImageId(final String imageUrl) {
        final Image image = Image.builder()
                .url(imageUrl)
                .width(100)
                .height(100)
                .extension("extension")
                .build();
        return imageRepository.save(image).getId();
    }
}
