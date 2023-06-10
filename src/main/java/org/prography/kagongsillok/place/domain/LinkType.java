package org.prography.kagongsillok.place.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkType {

    INSTAGRAM("인스타그램"),
    BLOG("블로그"),
    WEB("웹 사이트"),
    ;

    private final String description;
}
