package org.prography.kagongsillok.acceptance.tag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.태그_생성_요청_바디;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.review.ui.dto.ReviewTagCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewTagListResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewTagResponse;

public class ReviewTagAcceptanceTest extends AcceptanceTest {

    private static final String TAG_ADMIN_BASE_URL_V1 = "/admin/v1/tags";
    private static final String TAG_API_BASE_URL_V1 = "/api/v1/tags";

    @Test
    void 태그를_생성한다() {
        final var 태그_생성_요청_바디 = 태그_생성_요청_바디("#tag1", "test tag");

        final var 생성된_태그_응답 = 태그_생성_요청(태그_생성_요청_바디);

        생성된_태그_검증(생성된_태그_응답);
    }

    @Test
    void 태그_id로_태그를_조회한다() {
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 태그_생성_요청_바디3 = 태그_생성_요청_바디("#tag3", "test tag3");
        final var 생성된_태그_응답1 = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final var 생성된_태그_응답2 = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final var 생성된_태그_응답3 = 태그_생성_요청(태그_생성_요청_바디3).getId();
        final var 태그_ids = 생성된_태그_응답1 + "," + 생성된_태그_응답3;

        final var 조회된_태그들 = 태그_id로_태그_조회_요청(태그_ids);

        태그_id로_생성한_태그_조회_검증(조회된_태그들);
    }

    @Test
    void 모든_태그를_조회한다() {
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 태그_생성_요청_바디3 = 태그_생성_요청_바디("#tag3", "test tag3");
        태그_생성_요청(태그_생성_요청_바디1);
        태그_생성_요청(태그_생성_요청_바디2);
        태그_생성_요청(태그_생성_요청_바디3);

        final var 모든_태그_조회_응답 = 모든_태그_조회_요청();

        모든_태그_조회_검증(모든_태그_조회_응답);
    }

    @Test
    void 태그를_삭제한다() {
        final var 태그_생성_요청_바디 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 생성된_태그_id = 태그_생성_요청(태그_생성_요청_바디).getId();

        final var 태그_삭제_응답 = 태그_삭제_응답_요청(생성된_태그_id);
        final var 삭제후_태그_조회 = 삭제후_태그_조회(생성된_태그_id);

        태그_삭제_검증(태그_삭제_응답, 삭제후_태그_조회);
    }

    private ReviewTagListResponse 삭제후_태그_조회(final Long 생성된_태그_id) {
        return 응답_바디_추출(get(TAG_API_BASE_URL_V1 + "/" + 생성된_태그_id), ReviewTagListResponse.class);
    }

    private static void 태그_삭제_검증(final int 태그_삭제_응답, final ReviewTagListResponse 삭제후_태그_조회) {
        assertAll(
                () -> assertThat(태그_삭제_응답).isEqualTo(200),
                () -> assertThat(삭제후_태그_조회.getTags().size()).isEqualTo(0)
        );
    }

    private int 태그_삭제_응답_요청(final Long 생성된_태그_응답) {
        return delete(TAG_ADMIN_BASE_URL_V1 + "/" + 생성된_태그_응답).statusCode();
    }

    private static void 모든_태그_조회_검증(final ReviewTagListResponse 모든_태그_조회_응답) {
        assertAll(
                () -> assertThat(모든_태그_조회_응답.getTags().size()).isEqualTo(3),
                () -> assertThat(모든_태그_조회_응답.getTags()).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag2", "#tag3")),
                () -> assertThat(모든_태그_조회_응답.getTags()).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag2", "test tag3"))
        );
    }

    private ReviewTagListResponse 모든_태그_조회_요청() {
        return 응답_바디_추출(get(TAG_API_BASE_URL_V1 + "/all"), ReviewTagListResponse.class);
    }

    private static void 태그_id로_생성한_태그_조회_검증(final ReviewTagListResponse 조회된_태그들) {
        assertAll(
                () -> assertThat(조회된_태그들.getTags().size()).isEqualTo(2),
                () -> assertThat(조회된_태그들.getTags()).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag3")),
                () -> assertThat(조회된_태그들.getTags()).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag3"))
        );
    }

    private ReviewTagListResponse 태그_id로_태그_조회_요청(final String 태그_ids) {
        return 응답_바디_추출(get(TAG_API_BASE_URL_V1 + "/" + 태그_ids), ReviewTagListResponse.class);
    }

    private static void 생성된_태그_검증(final ReviewTagResponse 생성된_태그_응답) {
        assertAll(
                () -> assertThat(생성된_태그_응답.getTagName()).isEqualTo("#tag1"),
                () -> assertThat(생성된_태그_응답.getTagContent()).isEqualTo("test tag")
        );
    }

    private ReviewTagResponse 태그_생성_요청(final ReviewTagCreateRequest 태그_생성_요청_바디) {
        return 응답_바디_추출(post(TAG_ADMIN_BASE_URL_V1, 태그_생성_요청_바디), ReviewTagResponse.class);
    }
}
