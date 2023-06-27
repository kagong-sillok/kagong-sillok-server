package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Set;
import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    void 테그를_생성한다() {
        final Tags tags = Tags.of(Set.of("#태그1", "#태그2", "#태그3"));

        assertAll(
                () -> assertThat(tags.getTags().size()).isEqualTo(3),
                () -> assertThat(tags.getTags()).containsAll(Set.of("#태그1", "#태그2", "#태그3"))
        );
    }

}
