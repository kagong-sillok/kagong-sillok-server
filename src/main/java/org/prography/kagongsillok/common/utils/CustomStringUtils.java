package org.prography.kagongsillok.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomStringUtils {

    public static <T> List<T> splitToList(
            final String target,
            final String delimiter,
            final Function<String, T> mappingFunction
    ) {
        return Arrays.stream(target.split(delimiter))
                .map(mappingFunction)
                .collect(Collectors.toList());
    }
}
