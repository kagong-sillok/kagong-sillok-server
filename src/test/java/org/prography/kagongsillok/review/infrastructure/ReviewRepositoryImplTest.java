package org.prography.kagongsillok.review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagMapping;
import org.prography.kagongsillok.review.domain.ReviewTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRepositoryImplTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Autowired
    private ReviewRepositoryImpl reviewRepositoryImpl;

    @Autowired
    private MemberRepository memberRepository;

    private ReviewTag reviewTag1;
    private ReviewTag reviewTag2;
    private ReviewTag reviewTag3;
    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Member.builder()
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build());
        reviewTag1 = reviewTagRepository.save(new ReviewTag("testTag1", "testTag1"));
        reviewTag2 = reviewTagRepository.save(new ReviewTag("testTag2", "testTag2"));
        reviewTag3 = reviewTagRepository.save(new ReviewTag("testTag3", "testTag3"));
    }

    @Test
    void 리뷰_ID로_리뷰를_조회한다() {
        final Review review = Review.builder()
                .rating(4)
                .content("test review")
                .imageIds(List.of(1L, 2L, 3L))
                .tagMappings(List.of(new ReviewTagMapping(reviewTag1), new ReviewTagMapping(reviewTag2)))
                .memberId(member.getId())
                .placeId(1L)
                .memberNickName(member.getNickname())
                .build();
        final Long savedReviewId = reviewRepository.save(review).getId();

        final Review savedReview = reviewRepository.findById(savedReviewId)
                .orElseThrow(() -> new NotFoundReviewException(savedReviewId));

        assertAll(
                () -> assertThat(savedReview.getRating()).isEqualTo(4),
                () -> assertThat(savedReview.getMemberId()).isEqualTo(member.getId()),
                () -> assertThat(savedReview.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(savedReview.getPlaceId()).isEqualTo(1L),
                () -> assertThat(savedReview.getContent()).isEqualTo("test review"),
                () -> assertThat(savedReview.getImageIds()).containsAll(List.of(1L, 2L, 3L)),
                () -> assertThat(savedReview.getTagMappings().getValues()).hasSize(2),
                () -> assertThat(savedReview.getTagMappings().getValues()).extracting("reviewTag")
                        .extracting("tagName").containsAll(List.of("testTag1", "testTag2"))
        );
    }

    @Test
    void 멤버_ID로_작성한_리뷰들을_조회한다() {
        final Review review1 = Review.builder()
                .rating(4)
                .memberId(member.getId())
                .placeId(1L)
                .memberNickName(member.getNickname())
                .content("test review1")
                .imageIds(List.of(1L, 2L))
                .tagMappings(new ArrayList<>())
                .build();
        final Review review2 = Review.builder()
                .rating(2)
                .memberId(member.getId())
                .placeId(1L)
                .memberNickName(member.getNickname())
                .content("test review2")
                .imageIds(List.of(1L, 3L))
                .tagMappings(new ArrayList<>())
                .build();
        final Review review3 = Review.builder()
                .rating(5)
                .memberId(member.getId())
                .placeId(1L)
                .memberNickName(member.getNickname())
                .content("test review3")
                .imageIds(List.of(2L, 3L))
                .tagMappings(new ArrayList<>())
                .build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        final List<Review> reviews = reviewRepositoryImpl.findAllByMemberId(member.getId());

        assertAll(
                () -> assertThat(reviews.size()).isEqualTo(3),
                () -> assertThat(reviews).extracting("memberId")
                        .containsAll(List.of(member.getId(), member.getId(), member.getId())),
                () -> assertThat(reviews).extracting("memberNickName")
                        .containsAll(List.of("닉네임", "닉네임", "닉네임")),
                () -> assertThat(reviews).extracting("rating").containsAll(List.of(4, 2, 5)),
                () -> assertThat(reviews).extracting("imageIds")
                        .containsAll(List.of(List.of(1L, 2L), List.of(1L, 3L), List.of(2L, 3L))),
                () -> assertThat(reviews).extracting("content")
                        .containsAll(List.of("test review1", "test review2", "test review3"))
        );
    }
}
