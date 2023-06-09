package org.prography.kagongsillok.acceptance;

import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest.BusinessHourCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest.LinkCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest.BusinessHourUpdateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest.LinkUpdateRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AcceptanceTestFixture {

    public static ImageCreateRequest 넓이_100_높이_200_jpeg_이미지_생성_요청_바디(final String url) {
        return ImageCreateRequest.builder()
                .url(url)
                .width(100)
                .height(200)
                .extension("jpeg")
                .build();
    }

    public static PlaceCreateRequest 이미지_세개_링크_두개_장소_생성_요청_바디(
            final String name,
            final Double latitude,
            final Double longitude
    ) {
        return PlaceCreateRequest.builder()
                .name(name)
                .address("테스트특별시 테스트구 테스트로")
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(List.of(
                        new LinkCreateRequest(LinkType.INSTAGRAM.name(), "testInstagramUrl"),
                        new LinkCreateRequest(LinkType.BLOG.name(), "testBlogUrl"),
                        new LinkCreateRequest(LinkType.WEB.name(), "testWebUrl")))
                .businessHours(List.of(
                        new BusinessHourCreateRequest(
                                DayOfWeek.MONDAY.name(), "10:00:00", "21:00:00"
                        ),
                        new BusinessHourCreateRequest(
                                DayOfWeek.TUESDAY.name(), "10:00:00", "21:00:00"
                        ),
                        new BusinessHourCreateRequest(
                                DayOfWeek.WEDNESDAY.name(), "10:00:00", "21:00:00"
                        ),
                        new BusinessHourCreateRequest(
                                DayOfWeek.THURSDAY.name(), "10:00:00", "21:00:00"
                        ),
                        new BusinessHourCreateRequest(
                                DayOfWeek.FRIDAY.name(), "10:00:00", "21:00:00"
                        ),
                        new BusinessHourCreateRequest(
                                DayOfWeek.SATURDAY.name(), "10:00:00", "21:00:00"
                        ),
                        new BusinessHourCreateRequest(
                                DayOfWeek.SUNDAY.name(), "10:00:00", "21:00:00"
                        )
                ))
                .build();
    }

    public static PlaceUpdateRequest 장소_수정_요청_바디(
            final Long id,
            final String name,
            final Double latitude,
            final Double longitude
    ) {
        return PlaceUpdateRequest
                .builder()
                .id(id)
                .name(name)
                .address("새로운 테스트 특별시")
                .latitude(latitude)
                .longitude(longitude)
                .imageIds(List.of(4L, 5L, 6L))
                .phone("newTestPhoneNumber")
                .links(List.of(
                        new LinkUpdateRequest(LinkType.INSTAGRAM.name(), "newInstagramUrl"),
                        new LinkUpdateRequest(LinkType.BLOG.name(), "newBlogUrl"),
                        new LinkUpdateRequest(LinkType.WEB.name(), "newWebUrl"))
                )
                .businessHours(List.of(
                        new BusinessHourUpdateRequest(
                                DayOfWeek.MONDAY.name(), "11:00:00", "22:00:00"
                        ),
                        new BusinessHourUpdateRequest(
                                DayOfWeek.TUESDAY.name(), "11:00:00", "22:00:00"
                        ),
                        new BusinessHourUpdateRequest(
                                DayOfWeek.WEDNESDAY.name(), "11:00:00", "22:00:00"
                        ),
                        new BusinessHourUpdateRequest(
                                DayOfWeek.THURSDAY.name(), "11:00:00", "22:00:00"
                        ),
                        new BusinessHourUpdateRequest(
                                DayOfWeek.FRIDAY.name(), "11:00:00", "22:00:00"
                        ),
                        new BusinessHourUpdateRequest(
                                DayOfWeek.SATURDAY.name(), "11:00:00", "22:00:00"
                        ),
                        new BusinessHourUpdateRequest(
                                DayOfWeek.SUNDAY.name(), "11:00:00", "22:00:00"
                        )
                ))
                .build();
    }

}
