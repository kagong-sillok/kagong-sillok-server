package org.prography.kagongsillok.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.tag.application.dto.TagCreateCommand;
import org.prography.kagongsillok.tag.application.dto.TagDto;
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
}
