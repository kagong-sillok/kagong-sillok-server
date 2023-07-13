package org.prography.kagongsillok.record.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.exception.InvalidContentLengthException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecordDescription {

    private static final int MAX_DESCRIPTION_LENGTH = 10;
    private static final int MIN_DESCRIPTION_LENGTH = 1;

    @Column(name = "description")
    private String value;

    private StudyRecordDescription(final String value) {
        this.value = value;
    }

    public static StudyRecordDescription from(final String value) {
        validateValue(value);
        return new StudyRecordDescription(value);
    }

    private static void validateValue(final String value) {
        if (isUnderDescriptionLength(value) || isOverDescriptionLength(value)) {
            throw new InvalidContentLengthException(value);
        }
    }

    private static boolean isUnderDescriptionLength(final String value) {
        return value.length() < MIN_DESCRIPTION_LENGTH;
    }

    private static boolean isOverDescriptionLength(final String value) {
        return value.length() > MAX_DESCRIPTION_LENGTH;
    }
}
