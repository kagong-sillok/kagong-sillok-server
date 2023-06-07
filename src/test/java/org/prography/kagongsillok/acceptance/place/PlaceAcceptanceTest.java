package org.prography.kagongsillok.acceptance.place;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.*;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.이미지_세개_링크_두개_장소_생성_요청_바디;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;

public class PlaceAcceptanceTest extends AcceptanceTest {

    private static final String PLACE_ADMIN_BASE_URL_V1 = "/admin/v1/places";
    private static final String PLACE_API_BASE_URL_V1 = "/api/v1/places";

    @Test
    void 장소를_등록한다() {
        final var 장소_생성_요청_바디 = 이미지_세개_링크_두개_장소_생성_요청_바디("testPlace1", 30.0, 150.0);

        final var 생성된_장소_응답 = 장소_생성_요청(장소_생성_요청_바디);

        생성된_이미지_검증(생성된_장소_응답);
    }

    @Test
    void 장소_id로_장소_단건_조회를_한다() {
        final var 장소_생성_요청_바디1 = 이미지_세개_링크_두개_장소_생성_요청_바디("testPlace1", 30.0, 150.0);
        final var 장소_생성_요청_바디2 = 이미지_세개_링크_두개_장소_생성_요청_바디("testPlace2", 50.0, 110.0);
        final var 장소_생성_요청_바디3 = 이미지_세개_링크_두개_장소_생성_요청_바디("testPlace3", 80.0, 80.0);
        final var 생성된_장소_id1 = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 생성된_장소_id2 = 장소_생성_요청(장소_생성_요청_바디2).getId();
        final var 생성된_장소_id3 = 장소_생성_요청(장소_생성_요청_바디3).getId();

        final var 조회된_장소1 = 장소_조회(생성된_장소_id1);
        final var 조회된_장소2 = 장소_조회(생성된_장소_id2);
        final var 조회된_장소3 = 장소_조회(생성된_장소_id3);

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

    private PlaceResponse 장소_조회(final Long 생성된_장소_id) {
        return 응답_바디_추출(get(PLACE_ADMIN_BASE_URL_V1 + "/" + 생성된_장소_id), PlaceResponse.class);
    }

    private static void 생성된_이미지_검증(final PlaceResponse 생성된_장소_응답) {
        assertAll(
                () -> assertThat(생성된_장소_응답.getName()).isEqualTo("testPlace1"),
                () -> assertThat(생성된_장소_응답.getAddress()).isEqualTo("테스트특별시 테스트구 테스트로"),
                () -> assertThat(생성된_장소_응답.getLongitude()).isEqualTo(150.0),
                () -> assertThat(생성된_장소_응답.getLatitude()).isEqualTo(30.0),
                () -> assertThat(생성된_장소_응답.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
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