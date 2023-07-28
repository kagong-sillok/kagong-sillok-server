package org.prography.kagongsillok.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 리뷰를_생성한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final Long tagId2 = saveTagAndGetTagId("#tag2");
        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(5)
                .content("test review")
                .imageIds(List.of(1L, 2L))
                .reviewTagIds(List.of(tagId1, tagId2))
                .build();

        final ReviewDto reviewDto = reviewService.createReview(reviewCreateCommand);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(memberId),
                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
                () -> assertThat(reviewDto.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
                () -> assertThat(reviewDto.getImageIds()).containsAll(List.of(1L, 2L)),
                () -> assertThat(reviewDto.getTagIds()).containsAll(List.of(tagId1, tagId2))
        );
    }

    @Test
    void 리뷰를_조회한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(5)
                .content("test review")
                .imageIds(List.of(1L, 2L, 3L))
                .reviewTagIds(List.of(tagId1))
                .build();
        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();

        final ReviewDto reviewDto = reviewService.getReview(createdReviewId);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(memberId),
                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
                () -> assertThat(reviewDto.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
                () -> assertThat(reviewDto.getImageIds()).containsAll(List.of(1L, 2L, 3L)),
                () -> assertThat(reviewDto.getTagIds()).containsAll(List.of(tagId1))
        );
    }

    @Test
    void 리뷰를_수정한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final Long tagId2 = saveTagAndGetTagId("#tag2");
        final Long tagId3 = saveTagAndGetTagId("#tag3");
        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(5)
                .content("test review")
                .imageIds(List.of(1L, 2L))
                .reviewTagIds(List.of(tagId1, tagId2))
                .build();
        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();
        final ReviewUpdateCommand reviewUpdateCommand = ReviewUpdateCommand
                .builder()
                .memberId(memberId)
                .rating(4)
                .content("updated test review")
                .imageIds(List.of(1L, 3L))
                .reviewTagIds(List.of(tagId3))
                .build();

        final ReviewDto reviewDto = reviewService.updateReview(createdReviewId, reviewUpdateCommand);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(memberId),
                () -> assertThat(reviewDto.getRating()).isEqualTo(4),
                () -> assertThat(reviewDto.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(reviewDto.getContent()).isEqualTo("updated test review"),
                () -> assertThat(reviewDto.getImageIds()).containsAll(List.of(1L, 3L)),
                () -> assertThat(reviewDto.getTagIds()).containsAll(List.of(tagId3))
        );
    }

    @Test
    void 리뷰를_삭제한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(5)
                .content("test review")
                .imageIds(List.of(1L))
                .reviewTagIds(List.of(tagId1))
                .build();
        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();
        reviewService.deleteReview(createdReviewId);

        assertThatThrownBy(() -> reviewService.getReview(createdReviewId))
                .isInstanceOf(NotFoundReviewException.class)
                .hasMessageContaining(String.valueOf(createdReviewId));
    }

    @Test
    void 멤버_ID로_작성한_리뷰들을_조회한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final Long tagId2 = saveTagAndGetTagId("#tag2");
        final Long tagId3 = saveTagAndGetTagId("#tag3");
        final Long tagId4 = saveTagAndGetTagId("#tag4");
        final ReviewCreateCommand reviewCreateCommand1 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(5)
                .content("test review1")
                .imageIds(List.of(1L, 2L))
                .reviewTagIds(List.of(tagId1, tagId3))
                .build();
        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(1)
                .content("test review2")
                .imageIds(List.of(3L, 4L))
                .reviewTagIds(List.of(tagId4))
                .build();
        final ReviewCreateCommand reviewCreateCommand3 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(3)
                .content("test review3")
                .imageIds(List.of(5L))
                .reviewTagIds(List.of(tagId2, tagId3))
                .build();
        reviewService.createReview(reviewCreateCommand1);
        reviewService.createReview(reviewCreateCommand2);
        reviewService.createReview(reviewCreateCommand3);

        final List<ReviewDto> reviewDtos = reviewService.getAllReviewsByMemberId(memberId);

        assertAll(
                () -> assertThat(reviewDtos.size()).isEqualTo(3),
                () -> assertThat(reviewDtos).extracting("rating")
                        .containsAll(List.of(5, 1, 3)),
                () -> assertThat(reviewDtos).extracting("memberNickName")
                        .containsAll(List.of("닉네임", "닉네임", "닉네임")),
                () -> assertThat(reviewDtos).extracting("content")
                        .containsAll(List.of("test review1", "test review2", "test review3")),
                () -> assertThat(reviewDtos).extracting("imageIds")
                        .containsAll(List.of(List.of(1L, 2L), List.of(3L, 4L), List.of(5L))),
                () -> assertThat(reviewDtos).extracting("tagIds")
                        .containsAll(List.of(List.of(tagId1, tagId3), List.of(tagId4), List.of(tagId2, tagId3)))
        );
    }

    @Test
    void 장소_ID로_작성한_리뷰들을_조회한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long placeId = 1L;
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final Long tagId2 = saveTagAndGetTagId("#tag2");
        final Long tagId3 = saveTagAndGetTagId("#tag3");
        final Long tagId4 = saveTagAndGetTagId("#tag4");
        final ReviewCreateCommand reviewCreateCommand1 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(5)
                .content("test review1")
                .imageIds(List.of(1L, 2L))
                .reviewTagIds(List.of(tagId1, tagId3))
                .build();
        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(1)
                .content("test review2")
                .imageIds(List.of(3L, 4L))
                .reviewTagIds(List.of(tagId4))
                .build();
        final ReviewCreateCommand reviewCreateCommand3 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(3)
                .content("test review3")
                .imageIds(List.of(5L))
                .reviewTagIds(List.of(tagId2, tagId3))
                .build();
        reviewService.createReview(reviewCreateCommand1);
        reviewService.createReview(reviewCreateCommand2);
        reviewService.createReview(reviewCreateCommand3);

        final List<ReviewDto> reviewDtos = reviewService.getAllReviewsByMemberId(memberId);

        assertAll(
                () -> assertThat(reviewDtos.size()).isEqualTo(3),
                () -> assertThat(reviewDtos).extracting("placeId")
                        .containsAll(List.of(placeId, placeId, placeId)),
                () -> assertThat(reviewDtos).extracting("rating")
                        .containsAll(List.of(5, 1, 3)),
                () -> assertThat(reviewDtos).extracting("memberNickName")
                        .containsAll(List.of("닉네임", "닉네임", "닉네임")),
                () -> assertThat(reviewDtos).extracting("content")
                        .containsAll(List.of("test review1", "test review2", "test review3")),
                () -> assertThat(reviewDtos).extracting("imageIds")
                        .containsAll(List.of(List.of(1L, 2L), List.of(3L, 4L), List.of(5L))),
                () -> assertThat(reviewDtos).extracting("tagIds")
                        .containsAll(List.of(List.of(tagId1, tagId3), List.of(tagId4), List.of(tagId2, tagId3)))
        );
    }

    private Long saveTagAndGetTagId(final String tagName) {
        final ReviewTag reviewTag1 = new ReviewTag(tagName, "test tag");
        return reviewTagRepository.save(reviewTag1).getId();
    }

    private Long saveMemberAndGetMemberId() {
        final Member member = new Member("닉네임", "test@test.com", Role.MEMBER);
        return memberRepository.save(member).getId();
    }
}
