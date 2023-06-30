package org.prography.kagongsillok.review.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.exception.InvalidContentLengthException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    private static final int MAX_CONTENT_LENGTH = 200;
    private static final int MIN_CONTENT_LENGTH = 1;

    @Column(name = "content")
    private String value;

    private Content(final String value) {
        this.value = value;
    }

    public static Content from(final String value) {
        validateValue(value);
        return new Content(value);
    }

    private static void validateValue(final String value) {
        if (isUnderContentLength(value) || isOverContentLength(value)) {
            throw new InvalidContentLengthException(value);
        }
    }

    private static boolean isUnderContentLength(final String value) {
        return value.length() < MIN_CONTENT_LENGTH;
    }

    private static boolean isOverContentLength(final String value) {
        return value.length() > MAX_CONTENT_LENGTH;
    }
}
