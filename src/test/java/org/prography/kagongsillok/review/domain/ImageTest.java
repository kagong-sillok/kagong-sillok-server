package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.exception.InvalidLocationBoundException;
import org.prography.kagongsillok.review.domain.exception.InvalidNumberOfImagesException;

public class ImageTest {

    @Test
    void 정상정인_개수의_이미지를_생성한다() {
        final Images images = Images.of(Set.of("image1", "image2", "image3", "image4"));

        assertAll(
                () -> assertThat(images.getImages().size()).isEqualTo(4),
                () -> assertThat(images.getImages()).containsAll(Set.of("image1", "image2", "image3", "image4"))
        );
    }

    @Test
    void 비_정상정인_개수의_이미지를_생성한다() {
        Set imageSet = new LinkedHashSet(Set.of("image1", "image2", "image3", "image4", "image5", "image6"));

        assertThatThrownBy(() -> Images.of(imageSet))
                .isInstanceOf(InvalidNumberOfImagesException.class)
                .hasMessageContaining(String.valueOf(imageSet.size()));
    }

}
