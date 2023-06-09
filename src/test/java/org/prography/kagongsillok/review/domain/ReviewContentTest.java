package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.review.domain.exception.InvalidContentLengthException;
import org.prography.kagongsillok.review.domain.vo.ReviewContent;

public class ReviewContentTest {

    @ParameterizedTest
    @ValueSource(strings = {"content", "r"})
    void 리뷰_내용을_생성한다(final String rawContent) {
        final ReviewContent reviewContent = ReviewContent.from(rawContent);

        assertThat(reviewContent.getValue()).isEqualTo(rawContent);
    }

    @Test
    void 리뷰_내용_길이가_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> ReviewContent.from(""))
                .isInstanceOf(InvalidContentLengthException.class);
    }

    @Test
    void 리뷰_내용_길이가_200보다_길면_예외가_발생한다() {
        assertThatThrownBy(() -> ReviewContent.from("a".repeat(201)))
                .isInstanceOf(InvalidContentLengthException.class);
    }
}
