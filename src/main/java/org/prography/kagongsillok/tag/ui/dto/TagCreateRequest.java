package org.prography.kagongsillok.tag.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.tag.application.dto.TagCreateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCreateRequest {

    private String tagName;
    private String tagContent;

    public TagCreateRequest(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public TagCreateCommand toCommand() {
        return new TagCreateCommand(tagName, tagContent);
    }
}
