package org.prography.kagongsillok.tag.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    void 태그를_생성한다() {
        final Tag tag = new Tag("#tag", "test tag");

        assertAll(
                () -> assertThat(tag.getTagName()).isEqualTo("#tag"),
                () -> assertThat(tag.getTagContent()).isEqualTo("test tag")
        );
    }

}
