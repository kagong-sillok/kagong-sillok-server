package org.prography.kagongsillok.tag.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.tag.application.dto.TagCreateCommand;
import org.prography.kagongsillok.tag.application.dto.TagDto;
import org.prography.kagongsillok.tag.application.exception.NotFoundTagException;
import org.prography.kagongsillok.tag.domain.Tag;
import org.prography.kagongsillok.tag.domain.TagRepository;
import org.prography.kagongsillok.tag.infrastructure.TagRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagRepositoryImpl tagRepositoryImpl;

    @Transactional
    public TagDto createTag(final TagCreateCommand tagCreateCommand) {
        final Tag tag = tagCreateCommand.toEntity();
        final Tag savedTag = tagRepository.save(tag);

        return TagDto.from(tag);
    }

    public List<TagDto> getTags(final List<Long> tagIds) {
        final List<Tag> tags = tagRepository.findByIdIn(tagIds);

        for (Tag tag : tags) {
            if (tag.isDeleted()) {
                throw new NotFoundTagException(tag.getId());
            }
        }

        return CustomListUtils.mapTo(tags, TagDto::from);
    }

    public List<TagDto> getAllTags() {
        final List<Tag> tags = tagRepositoryImpl.findAllTags();

        return CustomListUtils.mapTo(tags, TagDto::from);
    }

    @Transactional
    public void deleteTag(final Long id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundTagException(id));

        tag.delete();
    }
}
