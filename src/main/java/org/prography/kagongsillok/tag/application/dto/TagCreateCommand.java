package org.prography.kagongsillok.tag.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.tag.domain.Tag;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCreateCommand {

    private String tagName;
    private String tagContent;

    public TagCreateCommand(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public Tag toEntity() {
        return new Tag(tagName, tagContent);
    }
}
