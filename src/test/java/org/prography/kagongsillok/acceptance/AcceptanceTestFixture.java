package org.prography.kagongsillok.acceptance;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.auth.ui.dto.KakaoLoginRequest;
import org.prography.kagongsillok.auth.ui.dto.LocalJoinRequest;
import org.prography.kagongsillok.auth.ui.dto.LocalLoginRequest;
import org.prography.kagongsillok.auth.ui.dto.LoginRefreshRequest;
import org.prography.kagongsillok.auth.ui.dto.LoginResultResponse;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest.BusinessHourCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest.LinkCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest.BusinessHourUpdateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest.LinkUpdateRequest;
import org.prography.kagongsillok.record.ui.dto.StudyRecordCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewTagCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewUpdateRequest;

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

    public static List<ImageCreateRequest> 이미지_두개_생성_요청_바디(final String url1, final String url2) {
        final ImageCreateRequest imageCreateRequest1 = ImageCreateRequest
                .builder()
                .url(url1)
                .width(100)
                .height(200)
                .extension("jpeg")
                .build();
        final ImageCreateRequest imageCreateRequest2 = ImageCreateRequest
                .builder()
                .url(url2)
                .width(100)
                .height(200)
                .extension("jpeg")
                .build();
        return new ArrayList<>(List.of(imageCreateRequest1, imageCreateRequest2));
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
                        new LinkCreateRequest(LinkType.INSTAGRAM.name(), "InstagramUrl"),
                        new LinkCreateRequest(LinkType.BLOG.name(), "BlogUrl"),
                        new LinkCreateRequest(LinkType.WEB.name(), "WebUrl")))
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

    public static ReviewCreateRequest 이미지_두개_태그_두개_리뷰_생성_요청_바디(
            final Long memberId,
            final String content,
            final List<Long> tagIds
    ) {
        return ReviewCreateRequest
                .builder()
                .rating(5)
                .memberId(memberId)
                .placeId(1L)
                .content(content)
                .imageIds(List.of(1L, 2L))
                .reviewTagIds(tagIds)
                .build();
    }

    public static ReviewUpdateRequest 리뷰_수정_요청_바디(
            final Long id,
            final int rating,
            final String content,
            final List<Long> tagIds
    ) {
        return ReviewUpdateRequest
                .builder()
                .id(id)
                .rating(rating)
                .memberId(2L)
                .placeId(1L)
                .content(content)
                .imageIds(List.of(1L, 2L))
                .reviewTagIds(tagIds)
                .build();
    }

    public static ReviewTagCreateRequest 태그_생성_요청_바디(
            final String tagName,
            final String tagContent
    ) {
        return new ReviewTagCreateRequest(tagName, tagContent);
    }

    public static LocalJoinRequest 기본_아이디_비밀번호_가입_요청_바디(final String nickname, final String email) {
        return LocalJoinRequest.builder()
                .loginId("loginId")
                .password("password")
                .nickname("닉네임")
                .email("test@test.com")
                .build();
    }

    public static LocalJoinRequest 기본_닉네임_이메일_가입_요청_바디(final String loginId, final String password) {
        return LocalJoinRequest.builder()
                .loginId("loginId")
                .password("password")
                .nickname("닉네임")
                .email("test@test.com")
                .build();
    }

    public static LocalLoginRequest 로그인_요청_바디(final String loginId, final String password) {
        return new LocalLoginRequest(loginId, password);
    }

    public static LoginRefreshRequest 토큰_갱신_바디(final LoginResultResponse loginResultResponse) {
        return new LoginRefreshRequest(loginResultResponse.getRefreshToken());
    }

    public static KakaoLoginRequest 카카오_로그인_요청_바디(final String authorizationCode, final String redirectUri) {
        return new KakaoLoginRequest(authorizationCode, redirectUri);
    }

    public static StudyRecordCreateRequest 공부_기록_생성_요청_바디(final Long memberId, final Long placeId,
            final String description) {
        return StudyRecordCreateRequest.builder()
                .memberId(memberId)
                .placeId(placeId)
                .studyYear(2023)
                .studyMonth(7)
                .studyDay(6)
                .duration(50)
                .description(description)
                .imageIds(List.of(1L, 2L))
                .build();
    }
}
