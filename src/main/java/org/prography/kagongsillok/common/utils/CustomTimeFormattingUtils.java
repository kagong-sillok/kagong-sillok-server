package org.prography.kagongsillok.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomTimeFormattingUtils {

    public static String LocalDateTimeToYearMonthDate(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

}
