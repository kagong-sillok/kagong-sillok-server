package org.prography.kagongsillok.common.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomListUtils {

    public static <T, R> List<R> mapTo(final List<T> list, final Function<T, R> mappingFunction) {
        if (isEmpty(list)) {
            return List.of();
        }

        return list.stream()
                .map(mappingFunction)
                .collect(Collectors.toList());
    }

    public static <T> boolean isEmpty(final List<T> list) {
        return Objects.isNull(list) || list.isEmpty();
    }

    public static <T> String joiningToString(final List<T> list, final String delimiter) {
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
    }
}
