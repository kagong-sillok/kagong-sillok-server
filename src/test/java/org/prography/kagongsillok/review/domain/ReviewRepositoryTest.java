package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 리뷰_ID로_리뷰를_조회한다() {
        final Member member = new Member("닉네임", "test@test.com", Role.MEMBER);
        final Long memberId = memberRepository.save(member).getId();
        final Review review1 = createReviewOfMemberIdAndContent(memberId, member.getNickname(), "test review");
        final Long savedReviewId = reviewRepository.save(review1).getId();

        final Review savedReview = reviewRepository.findById(savedReviewId)
                .orElseThrow(() -> new NotFoundReviewException(savedReviewId));

        assertAll(
                () -> assertThat(savedReview.getRating()).isEqualTo(5),
                () -> assertThat(savedReview.getMemberId()).isEqualTo(memberId),
                () -> assertThat(savedReview.getMemberNickName()).isEqualTo(member.getNickname()),
                () -> assertThat(savedReview.getContent()).isEqualTo("test review"),
                () -> assertThat(savedReview.getImageIds()).containsAll(List.of(1L, 2L, 3L)),
                () -> assertThat(savedReview.getTagMappings().getValues().size()).isEqualTo(0)
        );
    }

    @Test
    void 멤버_ID로_작성한_리뷰들을_조회한다() {
        final Member member = new Member("닉네임", "test@test.com", Role.MEMBER);
        final Long memberId = memberRepository.save(member).getId();
        final String memberNickName = member.getNickname();
        final Review review1 = createReviewOfMemberIdAndContent(memberId, memberNickName, "test review1");
        final Review review2 = createReviewOfMemberIdAndContent(memberId, memberNickName, "test review2");
        final Review review3 = createReviewOfMemberIdAndContent(memberId, memberNickName, "test review3");
        final Review review4 = createReviewOfMemberIdAndContent(memberId, memberNickName, "test review4");
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);

        final List<Review> reviews = reviewRepository.findAllByMemberId(memberId);

        assertAll(
                () -> assertThat(reviews.size()).isEqualTo(4),
                () -> assertThat(reviews).extracting("rating")
                        .containsAll(List.of(5, 5, 5, 5)),
                () -> assertThat(reviews).extracting("memberNickName")
                        .containsAll(List.of(memberNickName, memberNickName, memberNickName, memberNickName)),
                () -> assertThat(reviews).extracting("content")
                        .containsAll(List.of("test review1", "test review2", "test review3", "test review4"))
        );
    }

    private Review createReviewOfMemberIdAndContent(final Long memberId, final String memberNickName,
            final String content) {
        return Review.builder()
                .rating(5)
                .memberId(memberId)
                .placeId(1L)
                .memberNickName(memberNickName)
                .content(content)
                .imageIds(List.of(1L, 2L, 3L))
                .tagMappings(new ArrayList<>())
                .build();
    }
}
