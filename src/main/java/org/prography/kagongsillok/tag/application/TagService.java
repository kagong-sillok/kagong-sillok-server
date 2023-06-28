package org.prography.kagongsillok.tag.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.tag.application.dto.TagCreateCommand;
import org.prography.kagongsillok.tag.application.dto.TagDto;
import org.prography.kagongsillok.tag.domain.Tag;
import org.prography.kagongsillok.tag.domain.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public TagDto createTag(final TagCreateCommand tagCreateCommand) {
        final Tag tag = tagCreateCommand.toEntity();
        final Tag savedTag = tagRepository.save(tag);

        return TagDto.from(tag);
    }

    public List<TagDto> getTags(final List<Long> tagIds) {
        final List<Tag> tags = tagRepository.findByIdIn(tagIds);

        return CustomListUtils.mapTo(tags, TagDto::from);
    }
}