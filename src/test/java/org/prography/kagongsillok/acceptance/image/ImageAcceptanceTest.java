package org.prography.kagongsillok.acceptance.image;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.acceptance.AcceptanceTestFixture.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.acceptance.AcceptanceTest;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.image.ui.dto.ImageListResponse;
import org.prography.kagongsillok.image.ui.dto.ImageResponse;

public class ImageAcceptanceTest extends AcceptanceTest {

    private static final String IMAGE_API_BASE_URL_V1 = "/api/v1/images";

    @Test
    void 이미지를_등록한다() {
        final var 이미지_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl");

        final var 생성된_이미지_응답 = 이미지_생성_요청(이미지_생성_요청_바디);

        생성된_이미지_검증(생성된_이미지_응답);
    }

    @Test
    void 이미지_id_로_이미지_목록을_조회한다() {
        final var 이미지1_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl1");
        final var 이미지2_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl2");
        final var 이미지3_생성_요청_바디 = 넓이_100_높이_200_jpeg_이미지_생성_요청_바디("testImageUrl3");
        final var 생성된_이미지_id1 = 이미지_생성_요청(이미지1_생성_요청_바디).getId();
        final var 생성된_이미지_id2 = 이미지_생성_요청(이미지2_생성_요청_바디).getId();
        final var 생성된_이미지_id3 = 이미지_생성_요청(이미지3_생성_요청_바디).getId();

        final var 조회된_이미지_목록 = 이미지_목록_조회(생성된_이미지_id1, 생성된_이미지_id2, 생성된_이미지_id3);

        조회된_이미지_목록_검증(조회된_이미지_목록);
    }

    private ImageResponse 이미지_생성_요청(final ImageCreateRequest 이미지_생성_요청_바디) {
        return 응답_바디_추출(post(IMAGE_API_BASE_URL_V1, 이미지_생성_요청_바디), ImageResponse.class);
    }

    private List<ImageResponse> 이미지_목록_조회(final Long 이미지_id1, final Long 이미지_id2, final Long 이미지_id3) {
        final String imageIds = CustomListUtils.joiningToString(List.of(이미지_id1, 이미지_id2, 이미지_id3), ",");
        return 응답_바디_추출(get(IMAGE_API_BASE_URL_V1 + "?imageIds=" + imageIds), ImageListResponse.class)
                .getImages();
    }

    private void 생성된_이미지_검증(final ImageResponse 생성된_이미지) {
        assertAll(
                () -> assertThat(생성된_이미지.getUrl()).isEqualTo("testImageUrl"),
                () -> assertThat(생성된_이미지.getWidth()).isEqualTo(100),
                () -> assertThat(생성된_이미지.getHeight()).isEqualTo(200),
                () -> assertThat(생성된_이미지.getExtension()).isEqualTo("jpeg")
        );
    }

    private void 조회된_이미지_목록_검증(final List<ImageResponse> 조회된_이미지_목록) {
        assertAll(
                () -> assertThat(조회된_이미지_목록).hasSize(3),
                () -> assertThat(조회된_이미지_목록).extracting("url")
                        .containsAll(List.of("testImageUrl1", "testImageUrl2", "testImageUrl3"))
        );
    }
}
