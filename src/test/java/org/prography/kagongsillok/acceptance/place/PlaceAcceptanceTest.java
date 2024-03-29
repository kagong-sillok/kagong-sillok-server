package org.prography.kagongsillok.acceptance.place;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.기본_닉네임_이메일_가입_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.넓이_100_높이_200_jpeg_이미지_생성_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.링크_두개_장소_생성_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.장소_ID로_리뷰_생성_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.장소_수정_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.태그_생성_요청_바디;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.auth.ui.dto.LocalJoinRequest;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.image.ui.dto.ImageResponse;
import org.prography.kagongsillok.member.ui.dto.MemberResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceListResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceLocationAroundSearchRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceSearchResultResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewTagCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewTagResponse;

public class PlaceAcceptanceTest extends AcceptanceTest {

    private static final String PLACE_ADMIN_BASE_URL_V1 = "/admin/v1/places";
    private static final String PLACE_API_BASE_URL_V1 = "/api/v1/places";
    private static final String REVIEW_API_BASE_URL_V1 = "api/v1/reviews";
    private static final String TAG_ADMIN_BASE_URL_V1 = "admin/v1/tags";
    private static final String AUTH_API_BASE_URL_V1 = "/api/v1/auth";
    private static final String IMAGE_API_BASE_URL_V1 = "/api/v1/images";

    @Test
    void 관리자가_장소를_등록한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디 = 링크_두개_장소_생성_요청_바디("testPlace1", 30.0, 150.0, 생성된_이미지_ID);

        final var 생성된_장소_응답 = 장소_생성_요청(장소_생성_요청_바디);

        생성된_장소_검증(생성된_장소_응답, 생성된_이미지_ID);
    }

    @Test
    void 관리자가_장소_id로_장소_단건_조회를_한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디1 = 링크_두개_장소_생성_요청_바디("testPlace1", 30.0, 150.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디2 = 링크_두개_장소_생성_요청_바디("testPlace2", 50.0, 110.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디3 = 링크_두개_장소_생성_요청_바디("testPlace3", 80.0, 80.0, 생성된_이미지_ID);
        final var 생성된_장소_id1 = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 생성된_장소_id2 = 장소_생성_요청(장소_생성_요청_바디2).getId();
        final var 생성된_장소_id3 = 장소_생성_요청(장소_생성_요청_바디3).getId();

        final var 조회된_장소1 = 관리자_장소_조회(생성된_장소_id1);
        final var 조회된_장소2 = 관리자_장소_조회(생성된_장소_id2);
        final var 조회된_장소3 = 관리자_장소_조회(생성된_장소_id3);

        조회된_장소_검증(조회된_장소1, 조회된_장소2, 조회된_장소3);
    }

    private static void 조회된_장소_검증(
            final PlaceResponse 조회된_장소1,
            final PlaceResponse 조회된_장소2,
            final PlaceResponse 조회된_장소3
    ) {
        assertAll(
                () -> assertThat(조회된_장소1.getId()).isEqualTo(1L),
                () -> assertThat(조회된_장소1.getLatitude()).isEqualTo(30.0),
                () -> assertThat(조회된_장소1.getLongitude()).isEqualTo(150.0),
                () -> assertThat(조회된_장소2.getId()).isEqualTo(2L),
                () -> assertThat(조회된_장소2.getLatitude()).isEqualTo(50.0),
                () -> assertThat(조회된_장소2.getLongitude()).isEqualTo(110.0),
                () -> assertThat(조회된_장소3.getId()).isEqualTo(3L),
                () -> assertThat(조회된_장소3.getLatitude()).isEqualTo(80.0),
                () -> assertThat(조회된_장소3.getLongitude()).isEqualTo(80.0)
        );
    }

    @Test
    void 관리자가_장소를_수정_한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디 = 링크_두개_장소_생성_요청_바디("testPlace1", 30.0, 150.0, 생성된_이미지_ID);
        final var 생성된_장소_id = 장소_생성_요청(장소_생성_요청_바디).getId();

        final var 장소_수정_요청_바디 = 장소_수정_요청_바디(생성된_장소_id, "newTestPlace", 10.0, -10.0, 생성된_이미지_ID);
        final var 수정된_장소_응답 = 장소_수정_요청(장소_수정_요청_바디);

        수정된_장소_검증(수정된_장소_응답, 생성된_이미지_ID);
    }

    @Test
    void 관리자가_장소를_삭제_한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디 = 링크_두개_장소_생성_요청_바디("testPlace", 30.0, 150.0, 생성된_이미지_ID);
        final var 생성된_장소_id = 장소_생성_요청(장소_생성_요청_바디).getId();

        final var 장소_삭제_응답 = 장소_삭제_응답_요청(생성된_장소_id);
        final var 삭제후_조회한_장소 = 관리자_장소_조회(생성된_장소_id);

        장소_삭제_검증(장소_삭제_응답, 삭제후_조회한_장소);
    }

    @Test
    void 사용자가_장소_id로_장소_정보를_조회한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디1 = 링크_두개_장소_생성_요청_바디("testPlace1", 30.0, 150.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디2 = 링크_두개_장소_생성_요청_바디("testPlace2", 50.0, 110.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디3 = 링크_두개_장소_생성_요청_바디("testPlace3", 80.0, 80.0, 생성된_이미지_ID);
        final var 생성된_장소_id1 = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 생성된_장소_id2 = 장소_생성_요청(장소_생성_요청_바디2).getId();
        final var 생성된_장소_id3 = 장소_생성_요청(장소_생성_요청_바디3).getId();

        final var 조회된_장소1 = 사용자_장소_조회(생성된_장소_id1);
        final var 조회된_장소2 = 사용자_장소_조회(생성된_장소_id2);
        final var 조회된_장소3 = 사용자_장소_조회(생성된_장소_id3);

        조회된_장소_검증(조회된_장소1, 조회된_장소2, 조회된_장소3);
    }

    @Test
    void 사용자가_위도_경도_주변을_검색한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디1 = 링크_두개_장소_생성_요청_바디("testPlace1", 10.0, 10.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디2 = 링크_두개_장소_생성_요청_바디("testPlace2", 40.0, 50.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디3 = 링크_두개_장소_생성_요청_바디("testPlace3", -50.0, -50.0, 생성된_이미지_ID);
        final var 생성된_장소1 = 장소_생성_요청(장소_생성_요청_바디1);
        final var 생성된_장소2 = 장소_생성_요청(장소_생성_요청_바디2);
        final var 생성된_장소3 = 장소_생성_요청(장소_생성_요청_바디3);

        final var 탐색할_위도_경도_범위1 = 탐색할_위도_경도_범위_생성(5.0, 15.0, 10.0, 10.0);
        final var 탐색할_위도_경도_범위2 = 탐색할_위도_경도_범위_생성(70.0, 10.0, 35.0, 45.0);
        final var 탐색할_위도_경도_범위3 = 탐색할_위도_경도_범위_생성(-40.0, -40.0, 5.0, 15.0);

        final var 위도_경도_주변_검색_응답1 = 위도_경도_주변_검색_요청(탐색할_위도_경도_범위1);
        final var 위도_경도_주변_검색_응답2 = 위도_경도_주변_검색_요청(탐색할_위도_경도_범위2);
        final var 위도_경도_주변_검색_응답3 = 위도_경도_주변_검색_요청(탐색할_위도_경도_범위3);

        위도_경도_주변_검색_검증(위도_경도_주변_검색_응답1, 위도_경도_주변_검색_응답2, 위도_경도_주변_검색_응답3);
    }

    @Test
    void 사용자가_장소_이름으로_검색한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디1 = 링크_두개_장소_생성_요청_바디("testPlace1", 10.0, 10.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디2 = 링크_두개_장소_생성_요청_바디("testPlace1", 40.0, 50.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디3 = 링크_두개_장소_생성_요청_바디("testPlace2", -50.0, -50.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디4 = 링크_두개_장소_생성_요청_바디("testPlace2", -70.0, 100.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디5 = 링크_두개_장소_생성_요청_바디("testPlace2", -40.0, 130.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디6 = 링크_두개_장소_생성_요청_바디("testPlace2", -50.0, -9.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디7 = 링크_두개_장소_생성_요청_바디("testPlace3", 20.0, 28.0, 생성된_이미지_ID);
        final var 생성된_장소1 = 장소_생성_요청(장소_생성_요청_바디1);
        final var 생성된_장소2 = 장소_생성_요청(장소_생성_요청_바디2);
        final var 생성된_장소3 = 장소_생성_요청(장소_생성_요청_바디3);
        final var 생성된_장소4 = 장소_생성_요청(장소_생성_요청_바디4);
        final var 생성된_장소5 = 장소_생성_요청(장소_생성_요청_바디5);
        final var 생성된_장소6 = 장소_생성_요청(장소_생성_요청_바디6);
        final var 생성된_장소7 = 장소_생성_요청(장소_생성_요청_바디7);

        final var 장소_이름으로_검색_응답1 = 장소_이름으로_검색_요청("testPlace1");
        final var 장소_이름으로_검색_응답2 = 장소_이름으로_검색_요청("testPlace2");
        final var 장소_이름으로_검색_응답3 = 장소_이름으로_검색_요청("testPlace3");

        장소_이름으로_검색_검증(장소_이름으로_검색_응답1, 장소_이름으로_검색_응답2, 장소_이름으로_검색_응답3);
    }

    @Test
    void 사용자가_없는_장소_이름으로_검색한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 장소_생성_요청_바디1 = 링크_두개_장소_생성_요청_바디("testPlace1", 10.0, 10.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디2 = 링크_두개_장소_생성_요청_바디("testPlace2", -50.0, -50.0, 생성된_이미지_ID);
        final var 생성된_장소1 = 장소_생성_요청(장소_생성_요청_바디1);
        final var 생성된_장소2 = 장소_생성_요청(장소_생성_요청_바디2);

        final var 장소_이름으로_검색_응답1 = 장소_이름으로_검색_요청("testPlace3");

        없는_장소_이름으로_검색_검증(장소_이름으로_검색_응답1);
    }

    @Test
    void 사용자가_태그로_관련된_장소들을_검색한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");
        final var 생성된_이미지_ID = 이미지_생성_요청(이미지_생성_요청_바디).getId();
        final var 멤버_생성_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("닉네임", "test@test.com");
        final var 생성된_멤버_id = 멤버_생성_요청(멤버_생성_요청_바디).getId();
        final var 장소_생성_요청_바디1 = 링크_두개_장소_생성_요청_바디("testPlace1", 10.0, 10.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디2 = 링크_두개_장소_생성_요청_바디("testPlace2", -50.0, -50.0, 생성된_이미지_ID);
        final var 장소_생성_요청_바디3 = 링크_두개_장소_생성_요청_바디("testPlace3", -10.0, -20.0, 생성된_이미지_ID);
        final var 생성_장소1_ID = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 생성_장소2_ID = 장소_생성_요청(장소_생성_요청_바디2).getId();
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 태그_생성_요청_바디3 = 태그_생성_요청_바디("#tag3", "test tag3");
        final var 태그_생성_요청_바디4 = 태그_생성_요청_바디("#tag4", "test tag4");
        final var 생성된_태그1_ID = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final var 생성된_태그2_ID = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final var 생성된_태그3_ID = 태그_생성_요청(태그_생성_요청_바디3).getId();
        final var 생성된_태그4_ID = 태그_생성_요청(태그_생성_요청_바디4).getId();
        final var 입력_태그1 = 입력_태그_두개_생성(생성된_태그1_ID, 생성된_태그2_ID);
        final var 입력_태그2 = 입력_태그_두개_생성(생성된_태그3_ID, 생성된_태그4_ID);
        final var 리뷰_생성_요청_바디1 = 장소_ID로_리뷰_생성_요청_바디(생성_장소1_ID, 생성된_멤버_id, "test review", 입력_태그1, 생성된_이미지_ID);
        final var 리뷰_생성_요청_바디2 = 장소_ID로_리뷰_생성_요청_바디(생성_장소2_ID, 생성된_멤버_id, "test review", 입력_태그2, 생성된_이미지_ID);
        리뷰_생성_요청(리뷰_생성_요청_바디1);
        리뷰_생성_요청(리뷰_생성_요청_바디2);
        final var 조회할_태그_ID들 = 조회할_태그_생성(생성된_태그1_ID, 생성된_태그3_ID);

        final var 태그_ID로_장소_조회_응답 = 태그_ID로_장소_조회_요청(조회할_태그_ID들);

        태그_ID로_장소_조회_검증(태그_ID로_장소_조회_응답);
    }

    private ImageResponse 이미지_생성_요청(final ImageCreateRequest 이미지_생성_요청_바디) {
        return 응답_바디_추출(post(IMAGE_API_BASE_URL_V1, 이미지_생성_요청_바디), ImageResponse.class);
    }

    private MemberResponse 멤버_생성_요청(final LocalJoinRequest 멤버_생성_요청_바디) {
        return 응답_바디_추출(post(AUTH_API_BASE_URL_V1 + "/local/join", 멤버_생성_요청_바디), MemberResponse.class);
    }

    private static void 태그_ID로_장소_조회_검증(final PlaceListResponse 태그_ID로_장소_조회_응답) {
        assertAll(
                () -> assertThat(태그_ID로_장소_조회_응답.getPlaces().size()).isEqualTo(2),
                () -> assertThat(태그_ID로_장소_조회_응답.getPlaces()).extracting("name")
                        .containsAll(List.of("testPlace1", "testPlace2")),
                () -> assertThat(태그_ID로_장소_조회_응답.getPlaces()).extracting("latitude")
                        .containsAll(List.of(10.0, -50.0)),
                () -> assertThat(태그_ID로_장소_조회_응답.getPlaces()).extracting("longitude")
                        .containsAll(List.of(10.0, -50.0))
        );
    }

    private PlaceListResponse 태그_ID로_장소_조회_요청(final List<Long> 조회할_태그_ID들) {
        return 응답_바디_추출(get(PLACE_API_BASE_URL_V1 + "/tags"
                + "?tagIds=" + 조회할_태그_ID들.get(0)
                + "&tagIds=" + 조회할_태그_ID들.get(1)
        ), PlaceListResponse.class);
    }

    private List<Long> 조회할_태그_생성(final Long 생성된_태그1_ID, final Long 생성된_태그2_ID) {
        return List.of(생성된_태그1_ID, 생성된_태그2_ID);
    }

    private ReviewResponse 리뷰_생성_요청(final ReviewCreateRequest 리뷰_생성_요청_바디) {
        return 응답_바디_추출(post(REVIEW_API_BASE_URL_V1, 리뷰_생성_요청_바디), ReviewResponse.class);
    }

    private static List<Long> 입력_태그_두개_생성(final Long 태그1, final Long 태그2) {
        return List.of(태그1, 태그2);
    }

    private ReviewTagResponse 태그_생성_요청(final ReviewTagCreateRequest 태그_생성_요청_바디) {
        return 응답_바디_추출(post(TAG_ADMIN_BASE_URL_V1, 태그_생성_요청_바디), ReviewTagResponse.class);
    }

    private static void 없는_장소_이름으로_검색_검증(final PlaceSearchResultResponse 장소_이름으로_검색_응답1) {
        assertAll(
                () -> assertThat(장소_이름으로_검색_응답1.getPlaces().size()).isEqualTo(0)
        );
    }

    private static void 장소_이름으로_검색_검증(final PlaceSearchResultResponse 장소_이름으로_검색_응답1, final PlaceSearchResultResponse 장소_이름으로_검색_응답2,
            final PlaceSearchResultResponse 장소_이름으로_검색_응답3) {
        assertAll(
                () -> assertThat(장소_이름으로_검색_응답1.getPlaces().size()).isEqualTo(2),
                () -> assertThat(장소_이름으로_검색_응답1.getPlaces()).extracting("name")
                        .containsAll(List.of("testPlace1")),
                () -> assertThat(장소_이름으로_검색_응답1.getPlaces()).extracting("latitude")
                        .containsAll(List.of(10.0, 40.0)),
                () -> assertThat(장소_이름으로_검색_응답1.getPlaces()).extracting("longitude")
                        .containsAll(List.of(10.0, 50.0)),
                () -> assertThat(장소_이름으로_검색_응답2.getPlaces().size()).isEqualTo(4),
                () -> assertThat(장소_이름으로_검색_응답2.getPlaces()).extracting("name")
                        .containsAll(List.of("testPlace2")),
                () -> assertThat(장소_이름으로_검색_응답2.getPlaces()).extracting("latitude")
                        .containsAll(List.of(-50.0, -70.0, -40.0, -50.0)),
                () -> assertThat(장소_이름으로_검색_응답2.getPlaces()).extracting("longitude")
                        .containsAll(List.of(-50.0, 100.0, 130.0, -9.0)),
                () -> assertThat(장소_이름으로_검색_응답3.getPlaces().size()).isEqualTo(1),
                () -> assertThat(장소_이름으로_검색_응답3.getPlaces()).extracting("name")
                        .containsAll(List.of("testPlace3")),
                () -> assertThat(장소_이름으로_검색_응답3.getPlaces()).extracting("latitude")
                        .containsAll(List.of(20.0)),
                () -> assertThat(장소_이름으로_검색_응답3.getPlaces()).extracting("longitude")
                        .containsAll(List.of(28.0))
        );
    }

    private PlaceSearchResultResponse 장소_이름으로_검색_요청(
            final String name
    ) {
        return 응답_바디_추출(get(PLACE_API_BASE_URL_V1 + "?name=" + name), PlaceSearchResultResponse.class);
    }

    private static PlaceLocationAroundSearchRequest 탐색할_위도_경도_범위_생성(
            final double latitude,
            final double longitude,
            final double latitudeBound,
            final double longitudeBound
    ) {
        return PlaceLocationAroundSearchRequest
                .builder()
                .latitude(latitude)
                .longitude(longitude)
                .latitudeBound(latitudeBound)
                .longitudeBound(longitudeBound)
                .build();
    }


    private static void 위도_경도_주변_검색_검증(final PlaceListResponse 위도_경도_주변_검색_응답1, final PlaceListResponse 위도_경도_주변_검색_응답2,
            final PlaceListResponse 위도_경도_주변_검색_응답3) {
        assertAll(
                () -> assertThat(위도_경도_주변_검색_응답1.getPlaces().size()).isEqualTo(1),
                () -> assertThat(위도_경도_주변_검색_응답1.getPlaces().get(0).getName()).isEqualTo("testPlace1"),
                () -> assertThat(위도_경도_주변_검색_응답1.getPlaces().get(0).getLatitude()).isEqualTo(10.0),
                () -> assertThat(위도_경도_주변_검색_응답1.getPlaces().get(0).getLongitude()).isEqualTo(10.0),
                () -> assertThat(위도_경도_주변_검색_응답2.getPlaces().size()).isEqualTo(1),
                () -> assertThat(위도_경도_주변_검색_응답2.getPlaces().get(0).getName()).isEqualTo("testPlace2"),
                () -> assertThat(위도_경도_주변_검색_응답2.getPlaces().get(0).getLatitude()).isEqualTo(40.0),
                () -> assertThat(위도_경도_주변_검색_응답2.getPlaces().get(0).getLongitude()).isEqualTo(50.0),
                () -> assertThat(위도_경도_주변_검색_응답3.getPlaces().size()).isEqualTo(0)
        );
    }

    private static void 장소_삭제_검증(final int 장소_삭제_응답, final PlaceResponse 삭제후_조회한_장소) {
        assertAll(
                () -> assertThat(장소_삭제_응답).isEqualTo(200),
                () -> assertThat(삭제후_조회한_장소).isEqualTo(null)
        );
    }

    private PlaceListResponse 위도_경도_주변_검색_요청(PlaceLocationAroundSearchRequest request) {
        return 응답_바디_추출(get(PLACE_API_BASE_URL_V1 + "/around"
                        + "?latitude=" + request.getLatitude()
                        + "&longitude=" + request.getLongitude()
                        + "&latitudeBound=" + request.getLatitudeBound()
                        + "&longitudeBound=" + request.getLongitudeBound())
                , PlaceListResponse.class);
    }

    private int 장소_삭제_응답_요청(final Long 생성된_장소_id) {
        return delete(PLACE_ADMIN_BASE_URL_V1 + "/" + 생성된_장소_id).statusCode();
    }

    private static void 수정된_장소_검증(final PlaceResponse 수정된_장소_응답, final Long 생성된_이미지_id) {
        assertAll(
                () -> assertThat(수정된_장소_응답.getId()).isEqualTo(1L),
                () -> assertThat(수정된_장소_응답.getName()).isEqualTo("newTestPlace"),
                () -> assertThat(수정된_장소_응답.getLatitude()).isEqualTo(10.0),
                () -> assertThat(수정된_장소_응답.getLongitude()).isEqualTo(-10.0),
                () -> assertThat(수정된_장소_응답.getImages()).extracting("id")
                        .isEqualTo(List.of(생성된_이미지_id)),
                () -> assertThat(수정된_장소_응답.getLinks()).extracting("url")
                        .containsAll(List.of("newWebUrl", "newBlogUrl", "newInstagramUrl")),
                () -> assertThat(수정된_장소_응답.getBusinessHours()).extracting("open")
                        .containsAll(List.of("11:00:00", "11:00:00", "11:00:00", "11:00:00", "11:00:00", "11:00:00",
                                "11:00:00")),
                () -> assertThat(수정된_장소_응답.getBusinessHours()).extracting("close")
                        .containsAll(List.of("22:00:00", "22:00:00", "22:00:00", "22:00:00", "22:00:00", "22:00:00",
                                "22:00:00"))

        );
    }

    private PlaceResponse 장소_수정_요청(final PlaceUpdateRequest 장소_수정_요청_바디) {
        return 응답_바디_추출(put(PLACE_ADMIN_BASE_URL_V1, 장소_수정_요청_바디), PlaceResponse.class);
    }


    private PlaceResponse 관리자_장소_조회(final Long 생성된_장소_id) {
        return 응답_바디_추출(get(PLACE_ADMIN_BASE_URL_V1 + "/" + 생성된_장소_id), PlaceResponse.class);
    }

    private PlaceResponse 사용자_장소_조회(final Long 생성된_장소_id) {
        return 응답_바디_추출(get(PLACE_API_BASE_URL_V1 + "/" + 생성된_장소_id), PlaceResponse.class);
    }


    private static void 생성된_장소_검증(final PlaceResponse 생성된_장소_응답, final Long 생성된_이미지_id) {
        assertAll(
                () -> assertThat(생성된_장소_응답.getName()).isEqualTo("testPlace1"),
                () -> assertThat(생성된_장소_응답.getAddress()).isEqualTo("테스트특별시 테스트구 테스트로"),
                () -> assertThat(생성된_장소_응답.getLongitude()).isEqualTo(150.0),
                () -> assertThat(생성된_장소_응답.getLatitude()).isEqualTo(30.0),
                () -> assertThat(생성된_장소_응답.getImages()).extracting("id")
                        .isEqualTo(List.of(생성된_이미지_id)),
                () -> assertThat(생성된_장소_응답.getPhone()).isEqualTo("testPhoneNumber"),
                () -> assertThat(생성된_장소_응답.getLinks()).extracting("url")
                        .containsAll(List.of("WebUrl", "BlogUrl", "InstagramUrl")),
                () -> assertThat(생성된_장소_응답.getBusinessHours()).extracting("dayOfWeek")
                        .containsAll(
                                List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"))
        );
    }

    private PlaceResponse 장소_생성_요청(final PlaceCreateRequest 장소_생성_요청_바디) {
        return 응답_바디_추출(post(PLACE_ADMIN_BASE_URL_V1, 장소_생성_요청_바디), PlaceResponse.class);
    }
}
