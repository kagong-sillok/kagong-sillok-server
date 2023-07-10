package org.prography.kagongsillok.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.review.application.dto.ReviewTagCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewTagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewReviewTagServiceTest {

    @Autowired
    private ReviewTagService reviewTagService;

    @Test
    void 태그를_생성한다() {
        final ReviewTagCreateCommand reviewTagCreateCommand = new ReviewTagCreateCommand("#tag1", "test tag");

        final ReviewTagDto createdTag = reviewTagService.createTag(reviewTagCreateCommand);

        assertAll(
                () -> assertThat(createdTag.getTagName()).isEqualTo("#tag1"),
                () -> assertThat(createdTag.getTagContent()).isEqualTo("test tag")
        );
    }

    @Test
    void 태그_ID로_태그를_조회한다() {
        final ReviewTagCreateCommand reviewTagCreateCommand1 = new ReviewTagCreateCommand("#tag1", "test tag1");
        final ReviewTagCreateCommand reviewTagCreateCommand2 = new ReviewTagCreateCommand("#tag2", "test tag2");
        final ReviewTagCreateCommand reviewTagCreateCommand3 = new ReviewTagCreateCommand("#tag3", "test tag3");
        final Long createdTag1_id = reviewTagService.createTag(reviewTagCreateCommand1).getId();
        final Long createdTag2_id = reviewTagService.createTag(reviewTagCreateCommand2).getId();
        final Long createdTag3_id = reviewTagService.createTag(reviewTagCreateCommand3).getId();

        final List<ReviewTagDto> reviewTagDtos = reviewTagService.getTags(List.of(createdTag1_id, createdTag3_id));

        assertAll(
                () -> assertThat(reviewTagDtos).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag3")),
                () -> assertThat(reviewTagDtos).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag3"))
        );
    }

    @Test
    void 모든_태그를_조회한다() {
        final ReviewTagCreateCommand reviewTagCreateCommand1 = new ReviewTagCreateCommand("#tag1", "test tag1");
        final ReviewTagCreateCommand reviewTagCreateCommand2 = new ReviewTagCreateCommand("#tag2", "test tag2");
        final ReviewTagCreateCommand reviewTagCreateCommand3 = new ReviewTagCreateCommand("#tag3", "test tag3");
        reviewTagService.createTag(reviewTagCreateCommand1).getId();
        reviewTagService.createTag(reviewTagCreateCommand2).getId();
        reviewTagService.createTag(reviewTagCreateCommand3).getId();

        final List<ReviewTagDto> reviewTagDtos = reviewTagService.getAllTags();

        assertAll(
                () -> assertThat(reviewTagDtos).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag2", "#tag3")),
                () -> assertThat(reviewTagDtos).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag2", "test tag3"))
        );
    }

    @Test
    void 태그를_삭제한다() {
        final ReviewTagCreateCommand reviewTagCreateCommand1 = new ReviewTagCreateCommand("#tag1", "test tag1");
        final Long createdTag1_id = reviewTagService.createTag(reviewTagCreateCommand1).getId();

        reviewTagService.deleteTag(createdTag1_id);
        List<ReviewTagDto> reviewTagDtos = reviewTagService.getTags(List.of(createdTag1_id));

        assertAll(
                () -> assertThat(reviewTagDtos.size()).isEqualTo(0)
        );
    }
}
