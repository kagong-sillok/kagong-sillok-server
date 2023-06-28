package org.prography.kagongsillok.tag.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.tag.application.dto.TagDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagListResponse {

    public List<TagResponse> tags;

    public static TagListResponse from(final List<TagDto> tagDtos) {
        return new TagListResponse(CustomListUtils.mapTo(tagDtos, TagResponse::from));
    }
}
