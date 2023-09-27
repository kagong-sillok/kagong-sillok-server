package org.prography.kagongsillok.review.application;

import io.micrometer.core.annotation.Timed;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewTagCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewTagDto;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewTagException;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Timed("timer.reviewTag")
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewTagService {

    private final ReviewTagRepository reviewTagRepository;

    @Transactional
    public ReviewTagDto createTag(final ReviewTagCreateCommand reviewTagCreateCommand) {
        final ReviewTag reviewTag = reviewTagCreateCommand.toEntity();
        final ReviewTag savedReviewTag = reviewTagRepository.save(reviewTag);

        return ReviewTagDto.from(savedReviewTag);
    }

    public List<ReviewTagDto> getTags(final List<Long> tagIds) {
        final List<ReviewTag> reviewTags = reviewTagRepository.findByIdIn(tagIds);

        return CustomListUtils.mapTo(reviewTags, ReviewTagDto::from);
    }

    public List<ReviewTagDto> getAllTags() {
        final List<ReviewTag> reviewTags = reviewTagRepository.findAllTags();

        return CustomListUtils.mapTo(reviewTags, ReviewTagDto::from);
    }

    @Transactional
    public void deleteTag(final Long id) {
        final ReviewTag reviewTag = reviewTagRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewTagException(id));

        reviewTag.delete();
    }
}
