package org.prography.kagongsillok.acceptance;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest.BusinessHourCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest.LinkCreateRequest;

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
                        LinkCreateRequest.builder()
                                .url("WebUrl")
                                .linkType(LinkType.WEB.name())
                                .build(),
                        LinkCreateRequest.builder()
                                .url("BlogUrl")
                                .linkType(LinkType.BLOG.name())
                                .build(),
                        LinkCreateRequest.builder()
                                .url("InstagramUrl")
                                .linkType(LinkType.INSTAGRAM.name())
                                .build()
                ))
                .businessHours(List.of(
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.MONDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build(),
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.TUESDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build(),
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.WEDNESDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build(),
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.THURSDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build(),
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.FRIDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build(),
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.SATURDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build(),
                        BusinessHourCreateRequest.builder()
                                .dayOfWeek(DayOfWeek.SUNDAY.name())
                                .open("10:00:00")
                                .close("21:00:00")
                                .build()
                ))
                .build();
    }

}
