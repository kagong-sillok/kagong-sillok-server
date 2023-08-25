package org.prography.kagongsillok.review.application.dto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewTagMapping;
import org.prography.kagongsillok.review.domain.ReviewTagMappings;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

    private Long id;
    private Long memberId;
    private Long placeId;
    private String placeName;
    private String memberNickName;
    private String memberProfileUrl;
    private int rating;
    private String content;
    private List<ImageDto> images;
    private List<Long> tagIds;
    private ZonedDateTime writtenAt;

    @Builder
    public ReviewDto(
            final Long id,
            final Long memberId,
            final Long placeId,
            final String placeName,
            final String memberNickName,
            final String memberProfileUrl,
            final int rating,
            final String content,
            final List<ImageDto> images,
            final List<Long> tagIds,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.memberNickName = memberNickName;
        this.memberProfileUrl = memberProfileUrl;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.tagIds = tagIds;
        this.writtenAt = writtenAt;
    }

    public static ReviewDto of(Review review, final Member member, final List<Image> images) {
        return ReviewDto
                .builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .placeId(review.getPlaceId())
                .memberNickName(review.getMemberNickName())
                .memberProfileUrl(member.getProfileImageUrl())
                .rating(review.getRating())
                .content(review.getContent())
                .images(CustomListUtils.mapTo(images, ImageDto::from))
                .tagIds(getTagIds(review.getTagMappings()))
                .writtenAt(review.getWrittenAt())
                .build();
    }

    public static ReviewDto of(final Review review, final Member member, final List<Image> images, final Place place) {
        return ReviewDto
                .builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .placeId(review.getPlaceId())
                .placeName(place.getName())
                .memberNickName(review.getMemberNickName())
                .memberProfileUrl(member.getProfileImageUrl())
                .rating(review.getRating())
                .content(review.getContent())
                .images(CustomListUtils.mapTo(images, ImageDto::from))
                .tagIds(getTagIds(review.getTagMappings()))
                .writtenAt(review.getWrittenAt())
                .build();
    }

    private static List<Long> getTagIds(final ReviewTagMappings reviewTags) {
        List<ReviewTagMapping> reviewTagMappings = reviewTags.getValues();
        List<Long> tagIds = new ArrayList<>();

        for (ReviewTagMapping reviewTagMapping : reviewTagMappings) {
            tagIds.add(reviewTagMapping.getReviewTag().getId());
        }

        return tagIds;
    }
}
