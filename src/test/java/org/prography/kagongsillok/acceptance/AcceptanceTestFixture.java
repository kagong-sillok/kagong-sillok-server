package org.prography.kagongsillok.acceptance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;

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
}
