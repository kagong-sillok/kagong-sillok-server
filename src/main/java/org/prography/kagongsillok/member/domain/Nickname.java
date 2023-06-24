package org.prography.kagongsillok.member.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.domain.exception.TooLongNicknameException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    private static final int MAX_NICKNAME_LENGTH = 20;

    private String value;

    private Nickname(final String value) {
        this.value = value;
    }

    public static Nickname from(final String value) {
        validateValue(value);
        return new Nickname(value);
    }

    private static void validateValue(final String value) {
        if (isOverLength(value)) {
            throw new TooLongNicknameException(value);
        }
    }

    private static boolean isOverLength(final String value) {
        return value.length() > MAX_NICKNAME_LENGTH;
    }
}
