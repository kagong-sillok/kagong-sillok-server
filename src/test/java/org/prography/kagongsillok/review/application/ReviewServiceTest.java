package org.prography.kagongsillok.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.member.domain.Role;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.application.dto.ReviewImageListDto;
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

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PlaceRepository placeRepository;

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
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId1, tagId2))
                .build();

        final ReviewDto reviewDto = reviewService.createReview(reviewCreateCommand);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(memberId),
                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
                () -> assertThat(reviewDto.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
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
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId1))
                .build();
        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();

        final ReviewDto reviewDto = reviewService.getReview(createdReviewId);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(memberId),
                () -> assertThat(reviewDto.getRating()).isEqualTo(5),
                () -> assertThat(reviewDto.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(reviewDto.getContent()).isEqualTo("test review"),
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
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId1, tagId2))
                .build();
        final Long createdReviewId = reviewService.createReview(reviewCreateCommand).getId();
        final ReviewUpdateCommand reviewUpdateCommand = ReviewUpdateCommand
                .builder()
                .memberId(memberId)
                .rating(4)
                .content("updated test review")
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId3))
                .build();

        final ReviewDto reviewDto = reviewService.updateReview(createdReviewId, reviewUpdateCommand);

        assertAll(
                () -> assertThat(reviewDto.getMemberId()).isEqualTo(memberId),
                () -> assertThat(reviewDto.getRating()).isEqualTo(4),
                () -> assertThat(reviewDto.getMemberNickName()).isEqualTo("닉네임"),
                () -> assertThat(reviewDto.getContent()).isEqualTo("updated test review"),
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
                .imageIds(List.of())
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
        final Long placeId = savePlaceAndGetPlaceId();
        final Long memberId = saveMemberAndGetMemberId();
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
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId1, tagId3))
                .build();
        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(1)
                .content("test review2")
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId4))
                .build();
        final ReviewCreateCommand reviewCreateCommand3 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(3)
                .content("test review3")
                .imageIds(List.of())
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
                () -> assertThat(reviewDtos).extracting("tagIds")
                        .containsAll(List.of(List.of(tagId1, tagId3), List.of(tagId4), List.of(tagId2, tagId3)))
        );
    }

    @Test
    void 장소_ID로_작성한_리뷰들을_조회한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long placeId = savePlaceAndGetPlaceId();
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
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId1, tagId3))
                .build();
        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(1)
                .content("test review2")
                .imageIds(List.of())
                .reviewTagIds(List.of(tagId4))
                .build();
        final ReviewCreateCommand reviewCreateCommand3 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(3)
                .content("test review3")
                .imageIds(List.of())
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
                () -> assertThat(reviewDtos).extracting("tagIds")
                        .containsAll(List.of(List.of(tagId1, tagId3), List.of(tagId4), List.of(tagId2, tagId3)))
        );
    }

    @Test
    void 장소_ID로_해당_장소_리뷰_이미지들을_조회한다() {
        final Long memberId = saveMemberAndGetMemberId();
        final Long placeId = savePlaceAndGetPlaceId();
        final Long tagId1 = saveTagAndGetTagId("#tag1");
        final Long tagId2 = saveTagAndGetTagId("#tag2");
        final Long imageId1 = saveImageAndGetImageId("imageUrl1");
        final Long imageId2 = saveImageAndGetImageId("imageUrl2");
        final Long imageId3 = saveImageAndGetImageId("imageUrl3");
        final Long imageId4 = saveImageAndGetImageId("imageUrl4");
        final ReviewCreateCommand reviewCreateCommand1 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(5)
                .content("test review1")
                .imageIds(List.of(imageId1, imageId2))
                .reviewTagIds(List.of(tagId1))
                .build();
        final ReviewCreateCommand reviewCreateCommand2 = ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(1)
                .content("test review2")
                .imageIds(List.of(imageId3, imageId4))
                .reviewTagIds(List.of(tagId2))
                .build();
        reviewService.createReview(reviewCreateCommand1);
        reviewService.createReview(reviewCreateCommand2);

        final ReviewImageListDto reviewImageListDto = reviewService.getPlaceReviewImages(placeId);
        System.out.println(reviewImageListDto);

        assertAll(
                () -> assertThat(reviewImageListDto.getReviewImageDtos().size()).isEqualTo(4),
                () -> assertThat(reviewImageListDto.getReviewImageDtos())
                        .extracting("imageUrl")
                        .containsAll(List.of("imageUrl1", "imageUrl2", "imageUrl3", "imageUrl4")),
                () -> assertThat(reviewImageListDto.getReviewImageDtos())
                        .extracting("memberName")
                        .containsAll(List.of("닉네임", "닉네임", "닉네임", "닉네임")),
                () -> assertThat(reviewImageListDto.getTotalImageCount()).isEqualTo(4)
        );
    }

    private Long saveTagAndGetTagId(final String tagName) {
        final ReviewTag reviewTag1 = new ReviewTag(tagName, "test tag");
        return reviewTagRepository.save(reviewTag1).getId();
    }

    private Long saveMemberAndGetMemberId() {
        final Member member = Member.builder()
                .nickname("닉네임")
                .email("test@test.com")
                .role(Role.MEMBER)
                .build();
        return memberRepository.save(member).getId();
    }

    private Long saveImageAndGetImageId(final String imageUrl) {
        final Image image = Image.builder()
                .url(imageUrl)
                .width(100)
                .height(100)
                .extension("extension")
                .build();
        return imageRepository.save(image).getId();
    }

    private Long savePlaceAndGetPlaceId() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand.builder()
                .name("테스트 카페1")
                .address("테스트특별시 테스트구 테스트로 1004")
                .latitude(90.0)
                .longitude(120.129)
                .imageIds(List.of())
                .phone("010-1111-1111")
                .links(List.of(
                        new LinkCreateCommand(LinkType.INSTAGRAM.name(), "testInstagramUrl"),
                        new LinkCreateCommand(LinkType.BLOG.name(), "testBlogUrl"),
                        new LinkCreateCommand(LinkType.WEB.name(), "testWebUrl")
                ))
                .businessHours(List.of(
                        new BusinessHourCreateCommand(
                                DayOfWeek.MONDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.TUESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.WEDNESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.THURSDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.FRIDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.SATURDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        ),
                        new BusinessHourCreateCommand(
                                DayOfWeek.SUNDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
                        )
                ))
                .build();
        return placeRepository.save(placeCreateCommand1.toEntity()).getId();
    }
}
