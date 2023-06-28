package org.prography.kagongsillok.tag.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.tag.domain.Tag;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDto {

    private Long id;
    private String tagName;
    private String tagContent;

    public TagDto(final Long id, final String tagName, final String tagContent) {
        this.id = id;
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public static TagDto from(final Tag tag) {
        return new TagDto(tag.getId(), tag.getTagName(), tag.getTagContent());
    }
}
