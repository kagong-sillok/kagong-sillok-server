package org.prography.kagongsillok.review.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.image.application.exception.NotFoundImageException;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.member.application.exception.NotFoundMemberException;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
import org.prography.kagongsillok.review.application.dto.ReviewImageDto;
import org.prography.kagongsillok.review.application.dto.ReviewImageListDto;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;
import org.prography.kagongsillok.review.application.exception.NotFoundReviewException;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public ReviewDto createReview(final ReviewCreateCommand reviewCreateCommand) {
        final Map<Long, ReviewTag> reviewTagIds
                = reviewTagRepository.findByPerId(reviewCreateCommand.getReviewTagIds());

        final Long memberId = reviewCreateCommand.getMemberId();

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));

        checkExistImage(reviewCreateCommand.getImageIds());

        final Review review = reviewCreateCommand.toEntity(member.getNickname(), reviewTagIds);

        final Review savedReview = reviewRepository.save(review);
        final List<Image> images = imageRepository.findByIdIn(review.getImageIds());

        return ReviewDto.of(savedReview, member, images);
    }

    public ReviewDto getReview(final Long id) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));
        final Long memberId = review.getMemberId();
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        if (review.getIsDeleted()) {
            throw new NotFoundReviewException(id);
        }

        final List<Image> images = imageRepository.findByIdIn(review.getImageIds());

        return ReviewDto.of(review, member, images);
    }

    public List<ReviewDto> getAllReviewsByMemberId(final Long memberId) {
        final List<Review> reviews = reviewRepository.findAllByMemberId(memberId);

        if (reviews.isEmpty()) {
            return List.of();
        }

        final List<Long> memberIds = extractMemberIds(reviews);
        final List<Long> placeIds = extractPlaceIds(reviews);
        final List<Long> imageIds = extractAndFlatIds(reviews);

        final Map<Long, Member> memberMap = memberRepository.findByIdIn(memberIds);
        final Map<Long, Place> placeMap = placeRepository.findByIdInToMap(placeIds);
        final Map<Long, Image> imageMap = imageRepository.findByIdInToMap(imageIds);
        final Map<Long, List<Long>> reviewIdImageIdsMap = mapReviewIdImageIds(reviews);

        return toReviewDtos(reviews, memberMap, placeMap, imageMap, reviewIdImageIdsMap);
    }

    public List<ReviewDto> getAllReviewsByPlaceId(final Long placeId) {
        final List<Review> reviews = reviewRepository.findAllByPlaceId(placeId);
        final List<Long> reviewedMemberIds = getReviewedMemberIds(reviews);
        final Map<Long, Member> reviewedMembers = memberRepository.findByIdIn(reviewedMemberIds);

        return mappingMemberToReview(reviews, reviewedMembers);
    }

    @Transactional
    public ReviewDto updateReview(final Long id, final ReviewUpdateCommand reviewUpdateCommand) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));
        final Map<Long, ReviewTag> updateReviewTags
                = reviewTagRepository.findByPerId(reviewUpdateCommand.getReviewTagIds());

        checkExistImage(reviewUpdateCommand.getImageIds());

        final Review target = reviewUpdateCommand.toEntity(updateReviewTags);

        review.update(target);

        final Member member = memberRepository.findById(target.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException(target.getMemberId()));

        final List<Image> images = imageRepository.findByIdIn(review.getImageIds());

        return ReviewDto.of(review, member, images);
    }

    @Transactional
    public void deleteReview(final Long id) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));

        review.delete();
    }

    public ReviewImageListDto getPlaceReviewImages(final Long placeId) {
        final List<Review> reviews = reviewRepository.findAllByPlaceId(placeId);

        final List<Long> reviewImageIds = reviews.stream()
                .map(Review::getImageIds)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        final List<Long> memberIds = reviews.stream()
                .map(Review::getMemberId)
                .collect(Collectors.toList());

        final Map<Long, Image> images = imageRepository.findByIdInToMap(reviewImageIds);
        final Map<Long, Member> members = memberRepository.findByIdIn(memberIds);

        return ReviewImageListDto.of(
                reviewImageIds.size(),
                bindMemberAndImage(reviews, members, images)
        );
    }

    private List<ReviewImageDto> bindMemberAndImage(final List<Review> reviews, final Map<Long, Member> members,
            final Map<Long, Image> images) {

        final List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

        for (Review review : reviews) {
            reviewImageDtos.addAll(getReviewImageDtos(review, members, images));
        }

        return reviewImageDtos;
    }

    private Member getMappedMember(final Review review, final Map<Long, Member> members) {
        Long memberId = review.getMemberId();

        if (!members.containsKey(memberId)) {
            return Member.builder()
                    .nickname("알 수 없음")
                    .email("Unknown@unknown.com")
                    .build();
        }

        return members.get(memberId);
    }

    private List<Image> getMappedImage(final Review review, final Map<Long, Image> images) {
        return review
                .getImageIds()
                .stream()
                .filter(imageId -> images.containsKey(imageId))
                .map(images::get)
                .collect(Collectors.toList());
    }

    private List<ReviewImageDto> getReviewImageDtos(final Review review, final Map<Long, Member> members,
            final Map<Long, Image> images) {

        final List<ReviewImageDto> reviewImageDtos = new ArrayList<>();
        final Member mappedMember = getMappedMember(review, members);
        final List<Image> mappedImages = getMappedImage(review, images);

        for (Image mappedImage : mappedImages) {
            reviewImageDtos.add(ReviewImageDto.of(review.getId(), mappedMember, mappedImage));
        }

        return reviewImageDtos;
    }

    private List<Long> getReviewedMemberIds(final List<Review> reviews) {
        return reviews.stream()
                .map(review -> review.getMemberId())
                .collect(Collectors.toList());
    }

    private List<ReviewDto> mappingMemberToReview(final List<Review> reviews, final Map<Long, Member> members) {
        return reviews.stream()
                .map(review -> {
                    Member member = members.getOrDefault(review.getMemberId(), Member.defaultOf());
                    List<Image> images = imageRepository.findByIdIn(review.getImageIds());

                    return ReviewDto.of(review, member, images);
                })
                .collect(Collectors.toList());
    }

    private void checkExistImage(final List<Long> imageIds) {
        if (imageRepository.isNotExistIdIn(imageIds)) {
            throw new NotFoundImageException(imageIds);
        }
    }

    private List<Image> getMappedImages(final List<Long> imageIds, final Map<Long, Image> imageMap) {
        return imageIds.stream()
                .map(imageId -> imageMap.get(imageId))
                .collect(Collectors.toList());
    }

    private List<Long> extractMemberIds(final List<Review> reviews) {
        return reviews.stream()
                .map(Review::getMemberId)
                .collect(Collectors.toList());
    }

    private List<Long> extractPlaceIds(final List<Review> reviews) {
        return reviews.stream()
                .map(Review::getPlaceId)
                .collect(Collectors.toList());
    }

    private List<Long> extractAndFlatIds(final List<Review> reviews) {
        return reviews.stream()
                .map(Review::getImageIds)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private Map<Long, List<Long>> mapReviewIdImageIds(final List<Review> reviews) {
        return reviews
                .stream()
                .collect(Collectors.toMap(Review::getId, Review::getImageIds));
    }

    private List<ReviewDto> toReviewDtos(
            final List<Review> reviews,
            final Map<Long, Member> memberMap,
            final Map<Long, Place> placeMap,
            final Map<Long, Image> imageMap,
            Map<Long, List<Long>> reviewIdImageIdsMap
    ) {
        return reviews.stream()
                .map(review -> ReviewDto.of(
                        review,
                        memberMap.get(review.getMemberId()),
                        getMappedImages(reviewIdImageIdsMap.get(review.getId()), imageMap),
                        placeMap.get(review.getPlaceId())
                ))
                .collect(Collectors.toList());
    }
}
