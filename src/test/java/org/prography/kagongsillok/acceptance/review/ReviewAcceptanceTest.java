package org.prography.kagongsillok.acceptance.review;

//import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.이미지_두개_태그_두개_리뷰_생성_요청_바디;
//import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.리뷰_수정_요청_바디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewListResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewUpdateRequest;

public class ReviewAcceptanceTest extends AcceptanceTest {

    private static final String REVIEW_API_BASE_URL_V1 = "/api/v1/reviews";

//    @Test
//    void 리뷰를_생성한다() {
//        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(2L, "test review");
//
//        final var 생성된_리뷰_응답 = 리뷰_생성_요청(리뷰_생성_요청_바디);
//
//        생성된_리뷰_검증(생성된_리뷰_응답);
//    }
//
//    @Test
//    void 리뷰_id로_리뷰를_조회한다() {
//        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(2L, "test review");
//        final var 생성된_리뷰_id = 리뷰_생성_요청(리뷰_생성_요청_바디).getId();
//
//        final var 조회된_리뷰 = 리뷰_조회(생성된_리뷰_id);
//
//        조회된_리뷰_검증(조회된_리뷰);
//    }
//
//    @Test
//    void 리뷰를_수정한다() {
//        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(2L, "test review");
//        final var 생성된_리뷰_id = 리뷰_생성_요청(리뷰_생성_요청_바디).getId();
//
//        final var 리뷰_수정_요청_바디 = 리뷰_수정_요청_바디(생성된_리뷰_id, 2, "updated test review");
//        final var 수정된_리뷰_응답 = 리뷰_수정_요청(리뷰_수정_요청_바디);
//
//        수정된_리뷰_검증(수정된_리뷰_응답);
//    }
//
//    @Test
//    void 리뷰를_삭제한다() {
//        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(2L, "test review");
//        final var 생성된_리뷰_id = 리뷰_생성_요청(리뷰_생성_요청_바디).getId();
//
//        final var 리뷰_삭제_응답 = 리뷰_삭제_응답_요청(생성된_리뷰_id);
//        final var 삭제후_조회한_리뷰 = 리뷰_조회(생성된_리뷰_id);
//
//        리뷰_삭제_검증(리뷰_삭제_응답, 삭제후_조회한_리뷰);
//    }
//
//    @Test
//    void 멤버_id로_생성한_리뷰들을_조회한다() {
//        final var memberId = 1L;
//        final var 리뷰_생성_요청_바디1 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(memberId, "test review1");
//        final var 리뷰_생성_요청_바디2 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(memberId, "test review2");
//        final var 리뷰_생성_요청_바디3 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(memberId, "test review3");
//        리뷰_생성_요청(리뷰_생성_요청_바디1);
//        리뷰_생성_요청(리뷰_생성_요청_바디2);
//        리뷰_생성_요청(리뷰_생성_요청_바디3);
//
//        final var 멤버_id로_리뷰_조회_응답 = 멤버_id로_리뷰_조회_요청(memberId);
//
//        멤버_id로_생성한_리뷰_조회_검증(memberId, 멤버_id로_리뷰_조회_응답);
//    }
//
//    private static void 멤버_id로_생성한_리뷰_조회_검증(final long memberId, final ReviewListResponse 멤버_id로_리뷰_조회_응답) {
//        assertAll(
//                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews().size()).isEqualTo(3),
//                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews()).extracting("memberId")
//                        .containsAll(List.of(memberId, memberId, memberId)),
//                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews()).extracting("content")
//                        .containsAll(List.of("test review1", "test review2", "test review3"))
//        );
//    }
//
//    private ReviewListResponse 멤버_id로_리뷰_조회_요청(final Long memberId) {
//        return 응답_바디_추출(get(REVIEW_API_BASE_URL_V1 + "/member/" + memberId), ReviewListResponse.class);
//    }
//
//    private static void 리뷰_삭제_검증(final int 리뷰_삭제_응답, final ReviewResponse 삭제후_조회한_리뷰) {
//        assertAll(
//                () -> assertThat(리뷰_삭제_응답).isEqualTo(200),
//                () -> assertThat(삭제후_조회한_리뷰).isEqualTo(null)
//        );
//    }
//
//    private int 리뷰_삭제_응답_요청(final Long 생성된_리뷰_id) {
//        return delete(REVIEW_API_BASE_URL_V1 + "/" + 생성된_리뷰_id).statusCode();
//    }
//
//    private static void 수정된_리뷰_검증(final ReviewResponse 수정된_리뷰_응답) {
//        assertAll(
//                () -> assertThat(수정된_리뷰_응답.getId()).isEqualTo(1L),
//                () -> assertThat(수정된_리뷰_응답.getMemberId()).isEqualTo(2L),
//                () -> assertThat(수정된_리뷰_응답.getRating()).isEqualTo(2),
//                () -> assertThat(수정된_리뷰_응답.getContent()).isEqualTo("updated test review"),
//                () -> assertThat(수정된_리뷰_응답.getImageUrls()).containsAll(List.of("image1", "image2")),
//                () -> assertThat(수정된_리뷰_응답.getTags()).containsAll(List.of("#tag1", "#tag2")),
//                () -> assertThat(수정된_리뷰_응답.getUserNickName()).isEqualTo("임시 닉네임")
//        );
//    }
//
//    private ReviewResponse 리뷰_수정_요청(final ReviewUpdateRequest 리뷰_수정_요청_바디) {
//        return 응답_바디_추출(put(REVIEW_API_BASE_URL_V1, 리뷰_수정_요청_바디), ReviewResponse.class);
//    }
//
//    private static void 조회된_리뷰_검증(final ReviewResponse 조회된_리뷰) {
//        assertAll(
//                () -> assertThat(조회된_리뷰.getId()).isEqualTo(1L),
//                () -> assertThat(조회된_리뷰.getMemberId()).isEqualTo(2L),
//                () -> assertThat(조회된_리뷰.getRating()).isEqualTo(5),
//                () -> assertThat(조회된_리뷰.getContent()).isEqualTo("test review"),
//                () -> assertThat(조회된_리뷰.getImageUrls()).containsAll(List.of("image1", "image2")),
//                () -> assertThat(조회된_리뷰.getTags()).containsAll(List.of("#tag1", "#tag2")),
//                () -> assertThat(조회된_리뷰.getUserNickName()).isEqualTo("임시 닉네임")
//        );
//    }
//
//    private ReviewResponse 리뷰_조회(final Long 생성된_리뷰_id) {
//        return 응답_바디_추출(get(REVIEW_API_BASE_URL_V1 + "/" + 생성된_리뷰_id), ReviewResponse.class);
//    }
//
//    private static void 생성된_리뷰_검증(final ReviewResponse 생성된_리뷰_응답) {
//        assertAll(
//                () -> assertThat(생성된_리뷰_응답.getMemberId()).isEqualTo(2L),
//                () -> assertThat(생성된_리뷰_응답.getRating()).isEqualTo(5),
//                () -> assertThat(생성된_리뷰_응답.getContent()).isEqualTo("test review"),
//                () -> assertThat(생성된_리뷰_응답.getImageUrls()).containsAll(List.of("image1", "image2")),
//                () -> assertThat(생성된_리뷰_응답.getTags()).containsAll(List.of("#tag1", "#tag2")),
//                () -> assertThat(생성된_리뷰_응답.getUserNickName()).isEqualTo("임시 닉네임")
//        );
//    }
//
//    private ReviewResponse 리뷰_생성_요청(final ReviewCreateRequest 리뷰_생성_요청_바디) {
//        return 응답_바디_추출(post(REVIEW_API_BASE_URL_V1, 리뷰_생성_요청_바디), ReviewResponse.class);
//    }

}
