package org.prography.kagongsillok.tag.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.tag.application.dto.TagDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagResponse {

    private Long id;
    private String tagName;
    private String tagContent;

    public TagResponse(final Long id, final String tagName, final String tagContent) {
        this.id = id;
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public static TagResponse from(final TagDto tagDto) {
        return new TagResponse(tagDto.getId(), tagDto.getTagName(), tagDto.getTagContent());
    }
}
