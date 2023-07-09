package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class ReviewTagTest {

    @Test
    void 태그를_생성한다() {
        final ReviewTag reviewTag = new ReviewTag("#tag", "test tag");

        assertAll(
                () -> assertThat(reviewTag.getTagName()).isEqualTo("#tag"),
                () -> assertThat(reviewTag.getTagContent()).isEqualTo("test tag")
        );
    }

}
