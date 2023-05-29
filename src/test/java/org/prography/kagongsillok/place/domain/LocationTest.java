package org.prography.kagongsillok.place.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prography.kagongsillok.place.domain.exception.InvalidLocationBoundException;

class LocationTest {

    @Test
    void 위치를_생성한다() {
        final Location location = Location.of(90.0, 180.0);

        assertAll(
                () -> assertThat(location.getLatitude()).isEqualTo(90.0),
                () -> assertThat(location.getLongitude()).isEqualTo(180.0)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"90.1|180.0", "-90.1|180.0", "90.0|180.1", "90.0|-180.1"}, delimiterString = "|")
    void 위경도_범위를_벗어나는_위치는_생성할_수_없다(final double latitude, final double longitude) {
        assertThatThrownBy(() -> Location.of(latitude, longitude))
                .isInstanceOf(InvalidLocationBoundException.class)
                .hasMessageContaining(String.valueOf(latitude))
                .hasMessageContaining(String.valueOf(longitude));
    }
}
