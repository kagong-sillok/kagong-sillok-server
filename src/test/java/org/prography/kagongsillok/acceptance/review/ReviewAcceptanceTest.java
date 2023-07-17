package org.prography.kagongsillok.acceptance.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.기본_닉네임_이메일_가입_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.리뷰_수정_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.이미지_두개_태그_두개_리뷰_생성_요청_바디;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.태그_생성_요청_바디;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.auth.ui.dto.LocalJoinRequest;
import org.prography.kagongsillok.member.ui.dto.MemberResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewListResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewTagCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewTagResponse;
import org.prography.kagongsillok.review.ui.dto.ReviewUpdateRequest;

public class ReviewAcceptanceTest extends AcceptanceTest {

    private static final String REVIEW_API_BASE_URL_V1 = "/api/v1/reviews";
    private static final String TAG_API_BASE_URL_V1 = "/api/v1/tags";
    private static final String AUTH_API_BASE_URL_V1 = "/api/v1/auth";

    @Test
    void 리뷰를_생성한다() {
        final var 멤버_생성_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("닉네임", "test@test.com");
        final var 생성된_멤버_id = 멤버_생성_요청(멤버_생성_요청_바디).getId();
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 생성된_태그1_id = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final var 생성된_태그2_id = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final var 입력_태그 = 입력_태그_두개_생성(생성된_태그1_id, 생성된_태그2_id);
        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(생성된_멤버_id, "test review", 입력_태그);

        final var 생성된_리뷰_응답 = 리뷰_생성_요청(리뷰_생성_요청_바디);

        생성된_리뷰_검증(생성된_멤버_id, 생성된_리뷰_응답, 입력_태그);
    }

    @Test
    void 리뷰_id로_리뷰를_조회한다() {
        final var 멤버_생성_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("닉네임", "test@test.com");
        final var 생성된_멤버_id = 멤버_생성_요청(멤버_생성_요청_바디).getId();
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 생성된_태그1_id = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final var 생성된_태그2_id = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final var 입력_태그 = 입력_태그_두개_생성(생성된_태그1_id, 생성된_태그2_id);
        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(생성된_멤버_id, "test review", 입력_태그);
        final var 생성된_리뷰_id = 리뷰_생성_요청(리뷰_생성_요청_바디).getId();

        final var 조회된_리뷰 = 리뷰_조회(생성된_리뷰_id);

        조회된_리뷰_검증(생성된_멤버_id, 조회된_리뷰, 입력_태그);
    }

    @Test
    void 리뷰를_수정한다() {
        final var 멤버_생성_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("닉네임", "test@test.com");
        final var 생성된_멤버_id = 멤버_생성_요청(멤버_생성_요청_바디).getId();
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 태그_생성_요청_바디3 = 태그_생성_요청_바디("#tag3", "test tag3");
        final var 생성된_태그1_id = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final var 생성된_태그2_id = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final var 생성된_태그3_id = 태그_생성_요청(태그_생성_요청_바디3).getId();
        final var 입력_태그 = 입력_태그_두개_생성(생성된_태그1_id, 생성된_태그2_id);
        final var 수정_입력_태그 = 입력_태그_두개_생성(생성된_태그2_id, 생성된_태그3_id);
        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(생성된_멤버_id, "test review", 입력_태그);
        final var 생성된_리뷰_id = 리뷰_생성_요청(리뷰_생성_요청_바디).getId();

        final var 리뷰_수정_요청_바디 = 리뷰_수정_요청_바디(생성된_리뷰_id, 2, "updated test review", 수정_입력_태그);
        final var 수정된_리뷰_응답 = 리뷰_수정_요청(리뷰_수정_요청_바디);

        수정된_리뷰_검증(생성된_멤버_id, 수정된_리뷰_응답, 수정_입력_태그);
    }

    @Test
    void 리뷰를_삭제한다() {
        final var 멤버_생성_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("닉네임", "test@test.com");
        final var 생성된_멤버_id = 멤버_생성_요청(멤버_생성_요청_바디).getId();
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 생성된_태그1_id = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final var 생성된_태그2_id = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final var 입력_태그 = 입력_태그_두개_생성(생성된_태그1_id, 생성된_태그2_id);
        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(생성된_멤버_id, "test review", 입력_태그);
        final var 생성된_리뷰_id = 리뷰_생성_요청(리뷰_생성_요청_바디).getId();

        final var 리뷰_삭제_응답 = 리뷰_삭제_응답_요청(생성된_리뷰_id);
        final var 삭제후_리뷰_조회_응답코드 = 삭제후_리뷰_조회(생성된_리뷰_id);

        리뷰_삭제_검증(리뷰_삭제_응답, 삭제후_리뷰_조회_응답코드);
    }

    @Test
    void 멤버_id로_생성한_리뷰들을_조회한다() {
        final var 멤버_생성_요청_바디 = 기본_닉네임_이메일_가입_요청_바디("닉네임", "test@test.com");
        final var 생성된_멤버_id = 멤버_생성_요청(멤버_생성_요청_바디).getId();
        final var 태그_생성_요청_바디1 = 태그_생성_요청_바디("#tag1", "test tag1");
        final var 태그_생성_요청_바디2 = 태그_생성_요청_바디("#tag2", "test tag2");
        final var 태그_생성_요청_바디3 = 태그_생성_요청_바디("#tag3", "test tag3");
        final var 태그_생성_요청_바디4 = 태그_생성_요청_바디("#tag4", "test tag4");
        final Long 생성된_태그1_id = 태그_생성_요청(태그_생성_요청_바디1).getId();
        final Long 생성된_태그2_id = 태그_생성_요청(태그_생성_요청_바디2).getId();
        final Long 생성된_태그3_id = 태그_생성_요청(태그_생성_요청_바디3).getId();
        final Long 생성된_태그4_id = 태그_생성_요청(태그_생성_요청_바디4).getId();
        final var 입력_태그1 = 입력_태그_두개_생성(생성된_태그1_id, 생성된_태그2_id);
        final var 입력_태그2 = 입력_태그_두개_생성(생성된_태그3_id, 생성된_태그4_id);
//        final var memberId = 1L;
        final var 리뷰_생성_요청_바디1 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(생성된_멤버_id, "test review1", 입력_태그1);
        final var 리뷰_생성_요청_바디2 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(생성된_멤버_id, "test review2", 입력_태그2);
        리뷰_생성_요청(리뷰_생성_요청_바디1);
        리뷰_생성_요청(리뷰_생성_요청_바디2);

        final var 멤버_id로_리뷰_조회_응답 = 멤버_id로_리뷰_조회_요청(생성된_멤버_id);

        멤버_id로_생성한_리뷰_조회_검증(생성된_멤버_id, 멤버_id로_리뷰_조회_응답, 입력_태그1, 입력_태그2);
    }

    private MemberResponse 멤버_생성_요청(final LocalJoinRequest 멤버_생성_요청_바디) {
        return 응답_바디_추출(post(AUTH_API_BASE_URL_V1 + "/local/join", 멤버_생성_요청_바디), MemberResponse.class);
    }

    private ReviewTagResponse 태그_생성_요청(final ReviewTagCreateRequest 태그_생성_요청_바디) {
        return 응답_바디_추출(post(TAG_API_BASE_URL_V1, 태그_생성_요청_바디), ReviewTagResponse.class);
    }

    private static void 멤버_id로_생성한_리뷰_조회_검증(
            final Long memberId,
            final ReviewListResponse 멤버_id로_리뷰_조회_응답,
            final List<Long> 입력_태그1,
            final List<Long> 입력_태그2
    ) {
        assertAll(
                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews().size()).isEqualTo(2),
                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews()).extracting("memberId")
                        .containsAll(List.of(memberId, memberId)),
                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews()).extracting("content")
                        .containsAll(List.of("test review1", "test review2")),
                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews()).extracting("tagIds")
                        .containsAll(List.of(입력_태그1, 입력_태그2)),
                () -> assertThat(멤버_id로_리뷰_조회_응답.getReviews()).extracting("memberNickName")
                        .containsAll(List.of("닉네임", "닉네임"))
        );
    }

    private ReviewListResponse 멤버_id로_리뷰_조회_요청(final Long memberId) {
        return 응답_바디_추출(get(REVIEW_API_BASE_URL_V1 + "/member/" + memberId), ReviewListResponse.class);
    }

    private static void 리뷰_삭제_검증(final int 리뷰_삭제_응답, final int 삭제후_리뷰_조회_응답코드) {
        assertAll(
                () -> assertThat(리뷰_삭제_응답).isEqualTo(200),
                () -> assertThat(삭제후_리뷰_조회_응답코드).isEqualTo(404)
        );
    }

    private int 리뷰_삭제_응답_요청(final Long 생성된_리뷰_id) {
        return delete(REVIEW_API_BASE_URL_V1 + "/" + 생성된_리뷰_id).statusCode();
    }

    private static List<Long> 입력_태그_두개_생성(final Long 태그1, final Long 태그2) {
        return List.of(태그1, 태그2);
    }

    private static void 수정된_리뷰_검증(final Long 생성된_멤버_id, final ReviewResponse 수정된_리뷰_응답, final List<Long> 수정_입력_태그) {
        assertAll(
                () -> assertThat(수정된_리뷰_응답.getId()).isEqualTo(1L),
                () -> assertThat(수정된_리뷰_응답.getMemberId()).isEqualTo(생성된_멤버_id),
                () -> assertThat(수정된_리뷰_응답.getRating()).isEqualTo(2),
                () -> assertThat(수정된_리뷰_응답.getContent()).isEqualTo("updated test review"),
                () -> assertThat(수정된_리뷰_응답.getImageIds()).containsAll(List.of(1L, 2L)),
                () -> assertThat(수정된_리뷰_응답.getTagIds()).containsAll(수정_입력_태그),
                () -> assertThat(수정된_리뷰_응답.getMemberNickName()).isEqualTo("닉네임")
        );
    }

    private ReviewResponse 리뷰_수정_요청(final ReviewUpdateRequest 리뷰_수정_요청_바디) {
        return 응답_바디_추출(put(REVIEW_API_BASE_URL_V1, 리뷰_수정_요청_바디), ReviewResponse.class);
    }

    private static void 조회된_리뷰_검증(final Long 생성된_멤버_id, final ReviewResponse 조회된_리뷰, final List<Long> 입력_태그) {
        assertAll(
                () -> assertThat(조회된_리뷰.getId()).isEqualTo(1L),
                () -> assertThat(조회된_리뷰.getMemberId()).isEqualTo(생성된_멤버_id),
                () -> assertThat(조회된_리뷰.getRating()).isEqualTo(5),
                () -> assertThat(조회된_리뷰.getContent()).isEqualTo("test review"),
                () -> assertThat(조회된_리뷰.getImageIds()).containsAll(List.of(1L, 2L)),
                () -> assertThat(조회된_리뷰.getTagIds()).containsAll(입력_태그),
                () -> assertThat(조회된_리뷰.getMemberNickName()).isEqualTo("닉네임")
        );
    }

    private ReviewResponse 리뷰_조회(final Long 생성된_리뷰_id) {
        return 응답_바디_추출(get(REVIEW_API_BASE_URL_V1 + "/" + 생성된_리뷰_id), ReviewResponse.class);
    }

    private int 삭제후_리뷰_조회(final Long 생성된_리뷰_id) {
        return get(REVIEW_API_BASE_URL_V1 + "/" + 생성된_리뷰_id).statusCode();
    }

    private static void 생성된_리뷰_검증(final Long 생성된_멤버_id, final ReviewResponse 생성된_리뷰_응답, final List<Long> 입력_태그) {
        assertAll(
                () -> assertThat(생성된_리뷰_응답.getMemberId()).isEqualTo(생성된_멤버_id),
                () -> assertThat(생성된_리뷰_응답.getRating()).isEqualTo(5),
                () -> assertThat(생성된_리뷰_응답.getContent()).isEqualTo("test review"),
                () -> assertThat(생성된_리뷰_응답.getImageIds()).containsAll(List.of(1L, 2L)),
                () -> assertThat(생성된_리뷰_응답.getTagIds()).containsAll(입력_태그),
                () -> assertThat(생성된_리뷰_응답.getMemberNickName()).isEqualTo("닉네임")
        );
    }

    private ReviewResponse 리뷰_생성_요청(final ReviewCreateRequest 리뷰_생성_요청_바디) {
        return 응답_바디_추출(post(REVIEW_API_BASE_URL_V1, 리뷰_생성_요청_바디), ReviewResponse.class);
    }
}
