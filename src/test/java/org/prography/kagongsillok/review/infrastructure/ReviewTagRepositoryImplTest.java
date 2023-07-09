package org.prography.kagongsillok.review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.review.application.dto.ReviewTagCreateCommand;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewTagRepositoryImplTest {

    @Autowired
    private ReviewTagRepositoryImpl tagRepositoryImpl;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Test
    void 모든_태그를_조회한다() {
        final ReviewTagCreateCommand reviewTagCreateCommand1 = new ReviewTagCreateCommand("#tag1", "test tag1");
        final ReviewTagCreateCommand reviewTagCreateCommand2 = new ReviewTagCreateCommand("#tag2", "test tag2");
        final ReviewTagCreateCommand reviewTagCreateCommand3 = new ReviewTagCreateCommand("#tag3", "test tag3");
        reviewTagRepository.save(reviewTagCreateCommand1.toEntity());
        reviewTagRepository.save(reviewTagCreateCommand2.toEntity());
        reviewTagRepository.save(reviewTagCreateCommand3.toEntity());

        List<ReviewTag> reviewTags = tagRepositoryImpl.findAllTags();

        assertAll(
                () -> assertThat(reviewTags.size()).isEqualTo(3),
                () -> assertThat(reviewTags).extracting("tagName")
                        .containsAll(List.of("#tag1", "#tag2", "#tag3")),
                () -> assertThat(reviewTags).extracting("tagContent")
                        .containsAll(List.of("test tag1", "test tag2", "test tag3"))
        );
    }
}
