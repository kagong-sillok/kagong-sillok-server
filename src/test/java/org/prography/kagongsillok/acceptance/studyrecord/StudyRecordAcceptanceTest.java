package org.prography.kagongsillok.acceptance.studyrecord;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.공부_기록_생성_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.이미지_세개_링크_두개_장소_생성_요청_바디;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceListResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;
import org.prography.kagongsillok.record.ui.dto.StudyRecordCreateRequest;
import org.prography.kagongsillok.record.ui.dto.StudyRecordListResponse;
import org.prography.kagongsillok.record.ui.dto.StudyRecordResponse;

public class StudyRecordAcceptanceTest extends AcceptanceTest {

    private static final String PLACE_ADMIN_BASE_URL_V1 = "/admin/v1/places";
    private static final String STUDY_RECORD_API_BASE_URL_V1 = "/api/v1/study-records";

    @Test
    void 공부_기록을_생성한다() {
        final var 멤버_id = 1L;
        final var 장소_생성_요청_바디1 = 이미지_세개_링크_두개_장소_생성_요청_바디("place", 46.0, 28.0);
        final var 생성된_장소1_id = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 공부_기록_생성_요청_바디1 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코");

        final var 생성된_공부_기록_응답 = 공부_기록_생성_요청(공부_기록_생성_요청_바디1);

        공부_기록_생성_검증(생성된_공부_기록_응답);
    }

    @Test
    void 멤버_id로_공부_기록들을_조회한다() {
        final var 멤버_id = 1L;
        final var 장소_생성_요청_바디1 = 이미지_세개_링크_두개_장소_생성_요청_바디("place", 46.0, 28.0);
        final var 생성된_장소1_id = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 공부_기록_생성_요청_바디1 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코1");
        final var 공부_기록_생성_요청_바디2 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코2");
        공부_기록_생성_요청(공부_기록_생성_요청_바디1);
        공부_기록_생성_요청(공부_기록_생성_요청_바디2);

        final var 멤버_id로_공부_기록_조회_응답 = 멤버_id로_공부_기록_조회_요청(멤버_id);

        멤버_id로_공부_기록_조회_검증(멤버_id로_공부_기록_조회_응답);
    }

    @Test
    void 멤버_id와_년도와_월로_공부_기록들을_조회한다() {
        final var 멤버_id = 1L;
        final var 장소_생성_요청_바디1 = 이미지_세개_링크_두개_장소_생성_요청_바디("place", 46.0, 28.0);
        final var 생성된_장소1_id = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 공부_기록_생성_요청_바디1 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코1");
        final var 공부_기록_생성_요청_바디2 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코2");
        공부_기록_생성_요청(공부_기록_생성_요청_바디1);
        공부_기록_생성_요청(공부_기록_생성_요청_바디2);

        final var 멤버_id와_년도와_월로_공부_기록_조회_응답 = 멤버_id와_년도와_월로_공부_기록_조회_요청(멤버_id);

        멤버_id와_년도와_월로_공부_기록_조회_검증(멤버_id와_년도와_월로_공부_기록_조회_응답);
    }

    @Test
    void 멤버_id로_공부_기록의_장소들을_조회한다() {
        final var 멤버_id = 1L;
        final var 장소_생성_요청_바디1 = 이미지_세개_링크_두개_장소_생성_요청_바디("place1", 46.0, 28.0);
        final var 장소_생성_요청_바디2 = 이미지_세개_링크_두개_장소_생성_요청_바디("place2", 23.0, 21.0);
        final var 장소_생성_요청_바디3 = 이미지_세개_링크_두개_장소_생성_요청_바디("place3", -33.0, 10.0);
        final var 생성된_장소1_id = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 생성된_장소2_id = 장소_생성_요청(장소_생성_요청_바디2).getId();
        final var 생성된_장소3_id = 장소_생성_요청(장소_생성_요청_바디3).getId();
        final var 공부_기록_생성_요청_바디1 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코1");
        final var 공부_기록_생성_요청_바디2 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소2_id, "모각코2");
        final var 공부_기록_생성_요청_바디3 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소3_id, "모각코3");
        공부_기록_생성_요청(공부_기록_생성_요청_바디1);
        공부_기록_생성_요청(공부_기록_생성_요청_바디2);
        공부_기록_생성_요청(공부_기록_생성_요청_바디3);

        final var 멤버_id로_공부_기록의_장소_조회_응답 = 멤버_id로_공부_기록의_장소_조회_요청(멤버_id);

        멤버_id로_공부_기록의_장소_조회_검증(멤버_id로_공부_기록의_장소_조회_응답);
    }

    @Test
    void 공부_기록을_삭제한다() {
        final var 멤버_id = 1L;
        final var 장소_생성_요청_바디1 = 이미지_세개_링크_두개_장소_생성_요청_바디("place", 46.0, 28.0);
        final var 생성된_장소1_id = 장소_생성_요청(장소_생성_요청_바디1).getId();
        final var 공부_기록_생성_요청_바디1 = 공부_기록_생성_요청_바디(멤버_id, 생성된_장소1_id, "모각코");
        final var 생성된_공부_기록_id = 공부_기록_생성_요청(공부_기록_생성_요청_바디1).getId();

        final var 공부_기록_삭제_응답 = 공부_기록_삭제_응답_요청(생성된_공부_기록_id);
        final var 삭제후_멤버_id로_조회_응답 = 멤버_id로_공부_기록_조회_요청(멤버_id);

        공부_기록_삭제_검증(공부_기록_삭제_응답, 삭제후_멤버_id로_조회_응답);
    }

    private static void 공부_기록_삭제_검증(final int 공부_기록_삭제_응답, final StudyRecordListResponse 삭제후_멤버_id로_조회_응답) {
        assertAll(
                () -> assertThat(공부_기록_삭제_응답).isEqualTo(200),
                () -> assertThat(삭제후_멤버_id로_조회_응답.getStudyRecords().size()).isEqualTo(0)
        );
    }

    private int 공부_기록_삭제_응답_요청(final Long 생성된_공부_기록_id) {
        return delete(STUDY_RECORD_API_BASE_URL_V1 + "/" + 생성된_공부_기록_id).statusCode();
    }

    private static void 멤버_id로_공부_기록의_장소_조회_검증(final PlaceListResponse 멤버_id로_공부_기록의_장소_조회_응답) {
        assertAll(
                () -> assertThat(멤버_id로_공부_기록의_장소_조회_응답.getPlaces().size()).isEqualTo(3),
                () -> assertThat(멤버_id로_공부_기록의_장소_조회_응답.getPlaces())
                        .extracting("name").containsAll(List.of("place1", "place2", "place3")),
                () -> assertThat(멤버_id로_공부_기록의_장소_조회_응답.getPlaces())
                        .extracting("latitude").containsAll(List.of(46.0, 23.0, -33.0)),
                () -> assertThat(멤버_id로_공부_기록의_장소_조회_응답.getPlaces())
                        .extracting("longitude").containsAll(List.of(28.0, 21.0, 10.0))
        );
    }

    private PlaceListResponse 멤버_id로_공부_기록의_장소_조회_요청(final long 멤버_id) {
        return 응답_바디_추출(get(STUDY_RECORD_API_BASE_URL_V1 + "/places/" + 멤버_id), PlaceListResponse.class);
    }

    private static void 멤버_id와_년도와_월로_공부_기록_조회_검증(final StudyRecordListResponse 멤버_id와_년도와_월로_공부_기록_조회_응답) {
        assertAll(
                () -> assertThat(멤버_id와_년도와_월로_공부_기록_조회_응답.getStudyRecords().size()).isEqualTo(2),
                () -> assertThat(멤버_id와_년도와_월로_공부_기록_조회_응답.getStudyRecords())
                        .extracting("placeName").containsAll(List.of("place", "place")),
                () -> assertThat(멤버_id와_년도와_월로_공부_기록_조회_응답.getStudyRecords())
                        .extracting("description").containsAll(List.of("모각코1", "모각코2"))
        );
    }

    private StudyRecordListResponse 멤버_id와_년도와_월로_공부_기록_조회_요청(final long 멤버_id) {
        return 응답_바디_추출(get(STUDY_RECORD_API_BASE_URL_V1 + "/timelines/" + 멤버_id + "?year=2023&month=7"),
                StudyRecordListResponse.class);
    }

    private static void 멤버_id로_공부_기록_조회_검증(final StudyRecordListResponse 멤버_id로_공부_기록_조회_응답) {
        assertAll(
                () -> assertThat(멤버_id로_공부_기록_조회_응답.getStudyRecords().size()).isEqualTo(2),
                () -> assertThat(멤버_id로_공부_기록_조회_응답.getStudyRecords())
                        .extracting("placeName").containsAll(List.of("place", "place")),
                () -> assertThat(멤버_id로_공부_기록_조회_응답.getStudyRecords())
                        .extracting("description").containsAll(List.of("모각코1", "모각코2"))
        );
    }

    private StudyRecordListResponse 멤버_id로_공부_기록_조회_요청(final long 멤버_id) {
        return 응답_바디_추출(get(STUDY_RECORD_API_BASE_URL_V1 + "/member/" + 멤버_id), StudyRecordListResponse.class);
    }

    private static void 공부_기록_생성_검증(final StudyRecordResponse 생성된_공부_기록_응답) {
        assertAll(
                () -> assertThat(생성된_공부_기록_응답.getId()).isEqualTo(1L),
                () -> assertThat(생성된_공부_기록_응답.getPlaceName()).isEqualTo("place"),
                () -> assertThat(생성된_공부_기록_응답.getStudyDate()).isEqualTo(LocalDate.of(2023, 7, 6)),
                () -> assertThat(생성된_공부_기록_응답.getDuration()).isEqualTo(50),
                () -> assertThat(생성된_공부_기록_응답.getDescription()).isEqualTo("모각코"),
                () -> assertThat(생성된_공부_기록_응답.getImageIds()).isEqualTo(List.of())
        );
    }

    private PlaceResponse 장소_생성_요청(final PlaceCreateRequest 장소_생성_요청_바디) {
        return 응답_바디_추출(post(PLACE_ADMIN_BASE_URL_V1, 장소_생성_요청_바디), PlaceResponse.class);
    }

    private StudyRecordResponse 공부_기록_생성_요청(final StudyRecordCreateRequest 공부_기록_생성_요청_바디) {
        return 응답_바디_추출(post(STUDY_RECORD_API_BASE_URL_V1, 공부_기록_생성_요청_바디), StudyRecordResponse.class);
    }
}
