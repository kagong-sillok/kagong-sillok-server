package org.prography.kagongsillok.tag.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.tag.application.dto.TagCreateCommand;
import org.prography.kagongsillok.tag.domain.Tag;
import org.prography.kagongsillok.tag.domain.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TagRepositoryImplTest {

    @Autowired
    private TagRepositoryImpl tagRepositoryImpl;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void 모든_태그를_조회한다() {
        final TagCreateCommand tagCreateCommand1 = new TagCreateCommand("#tag1", "test tag1");
        final TagCreateCommand tagCreateCommand2 = new TagCreateCommand("#tag2", "test tag2");
        final TagCreateCommand tagCreateCommand3 = new TagCreateCommand("#tag3", "test tag3");
        tagRepository.save(tagCreateCommand1.toEntity());
        tagRepository.save(tagCreateCommand2.toEntity());
        tagRepository.save(tagCreateCommand3.toEntity());

        List<Tag> tags = tagRepositoryImpl.findAllTags();

        assertAll(
                () -> assertThat(tags.size()).isEqualTo(3),
                () -> assertThat(tags).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag2", "#tag3")),
                () -> assertThat(tags).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag2", "test tag3"))
        );
    }
}
