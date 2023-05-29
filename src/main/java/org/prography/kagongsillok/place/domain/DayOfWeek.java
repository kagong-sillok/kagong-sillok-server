package org.prography.kagongsillok.place.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek {

    MONDAY("monday", "월요일"),
    TUESDAY("tuesday", "화요일"),
    WEDNESDAY("wednesday", "수요일"),
    THURSDAY("thursday", "목요일"),
    FRIDAY("friday", "금요일"),
    SATURDAY("saturday", "토요일"),
    SUNDAY("sunday", "일요일"),
    ;

    private final String eng;
    private final String kor;
}
