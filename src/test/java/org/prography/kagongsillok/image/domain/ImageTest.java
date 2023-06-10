package org.prography.kagongsillok.image.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void 이미지를_생성한다() {
        final Image image = Image.builder()
                .url("imageUrl")
                .height(100)
                .width(100)
                .extension("jpeg")
                .build();

        assertAll(
                () -> assertThat(image.getUrl()).isEqualTo("imageUrl"),
                () -> assertThat(image.getHeight()).isEqualTo(100),
                () -> assertThat(image.getWidth()).isEqualTo(100),
                () -> assertThat(image.getExtension()).isEqualTo("jpeg")
        );
    }
}
