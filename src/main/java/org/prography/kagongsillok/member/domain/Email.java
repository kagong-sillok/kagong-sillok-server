package org.prography.kagongsillok.member.domain;

import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.exception.InvalidParamException;
import org.prography.kagongsillok.member.domain.exception.InvalidEmailException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    private String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email from(final String value) {
        validateValue(value);
        return new Email(value);
    }

    private static void validateValue(final String value) {
        if (!isEmailForm(value)) {
            throw new InvalidEmailException(value);
        }
    }

    private static boolean isEmailForm(String value) {
        return Pattern.matches(EMAIL_REGEX, value);
    }
}
