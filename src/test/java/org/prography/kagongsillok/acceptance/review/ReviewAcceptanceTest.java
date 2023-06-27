package org.prography.kagongsillok.acceptance.review;

import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.이미지_두개_태그_두개_리뷰_생성_요청_바디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.review.ui.dto.ReviewCreateRequest;
import org.prography.kagongsillok.review.ui.dto.ReviewResponse;

public class ReviewAcceptanceTest extends AcceptanceTest {

    private static final String REVIEW_API_BASE_URL_V1 = "/api/v1/reviews";

    @Test
    void 리뷰를_생성한다() {
        final var 리뷰_생성_요청_바디 = 이미지_두개_태그_두개_리뷰_생성_요청_바디(2L, "test review");

        final var 생성된_리뷰_응답 = 리뷰_생성_요청(리뷰_생성_요청_바디);

        생성된_리뷰_검증(생성된_리뷰_응답);
    }

    private static void 생성된_리뷰_검증(final ReviewResponse 생성된_리뷰_응답) {
        assertAll(
                () -> assertThat(생성된_리뷰_응답.getMemberId()).isEqualTo(2L),
                () -> assertThat(생성된_리뷰_응답.getRating()).isEqualTo(5),
                () -> assertThat(생성된_리뷰_응답.getContent()).isEqualTo("test review"),
                () -> assertThat(생성된_리뷰_응답.getImageUrls()).containsAll(List.of("image1", "image2")),
                () -> assertThat(생성된_리뷰_응답.getTags()).containsAll(List.of("#tag1", "#tag2")),
                () -> assertThat(생성된_리뷰_응답.getUserNickName()).isEqualTo("임시 닉네임")
        );
    }

    private ReviewResponse 리뷰_생성_요청(final ReviewCreateRequest 리뷰_생성_요청_바디) {
        return 응답_바디_추출(post(REVIEW_API_BASE_URL_V1, 리뷰_생성_요청_바디), ReviewResponse.class);
    }

}
