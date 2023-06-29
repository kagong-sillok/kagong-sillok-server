package org.prography.kagongsillok.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.tag.application.dto.TagCreateCommand;
import org.prography.kagongsillok.tag.application.dto.TagDto;
import org.prography.kagongsillok.tag.application.exception.NotFoundTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    void 태그를_생성한다() {
        final TagCreateCommand tagCreateCommand = new TagCreateCommand("#tag1", "test tag");

        final TagDto createdTag = tagService.createTag(tagCreateCommand);

        assertAll(
                () -> assertThat(createdTag.getTagName()).isEqualTo("#tag1"),
                () -> assertThat(createdTag.getTagContent()).isEqualTo("test tag")
        );
    }

    @Test
    void 태그_ID로_태그를_조회한다() {
        final TagCreateCommand tagCreateCommand1 = new TagCreateCommand("#tag1", "test tag1");
        final TagCreateCommand tagCreateCommand2 = new TagCreateCommand("#tag2", "test tag2");
        final TagCreateCommand tagCreateCommand3 = new TagCreateCommand("#tag3", "test tag3");
        final Long createdTag1_id = tagService.createTag(tagCreateCommand1).getId();
        final Long createdTag2_id = tagService.createTag(tagCreateCommand2).getId();
        final Long createdTag3_id = tagService.createTag(tagCreateCommand3).getId();

        final List<TagDto> tagDtos = tagService.getTags(List.of(createdTag1_id, createdTag3_id));

        assertAll(
                () -> assertThat(tagDtos).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag3")),
                () -> assertThat(tagDtos).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag3"))
        );
    }

    @Test
    void 모든_태그를_조회한다() {
        final TagCreateCommand tagCreateCommand1 = new TagCreateCommand("#tag1", "test tag1");
        final TagCreateCommand tagCreateCommand2 = new TagCreateCommand("#tag2", "test tag2");
        final TagCreateCommand tagCreateCommand3 = new TagCreateCommand("#tag3", "test tag3");
        tagService.createTag(tagCreateCommand1).getId();
        tagService.createTag(tagCreateCommand2).getId();
        tagService.createTag(tagCreateCommand3).getId();

        final List<TagDto> tagDtos = tagService.getAllTags();

        assertAll(
                () -> assertThat(tagDtos).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag2", "#tag3")),
                () -> assertThat(tagDtos).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag2", "test tag3"))
        );
    }

    @Test
    void 태그를_삭제한다() {
        final TagCreateCommand tagCreateCommand1 = new TagCreateCommand("#tag1", "test tag1");
        final Long createdTag1_id = tagService.createTag(tagCreateCommand1).getId();

        tagService.deleteTag(createdTag1_id);

        assertThatThrownBy(() -> tagService.getTags(List.of(createdTag1_id)))
                .isInstanceOf(NotFoundTagException.class)
                .hasMessageContaining(String.valueOf(createdTag1_id));
    }
}
