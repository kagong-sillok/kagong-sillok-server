package org.prography.kagongsillok.review.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.member.application.exception.NotFoundMemberException;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
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

    @Transactional
    public ReviewDto createReview(final ReviewCreateCommand reviewCreateCommand) {
        final Map<Long, ReviewTag> reviewTagIds
                = reviewTagRepository.findByPerId(reviewCreateCommand.getReviewTagIds());

        final Long memberId = reviewCreateCommand.getMemberId();

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        final Review review = reviewCreateCommand.toEntity(member.getNickname(), reviewTagIds);

        final Review savedReview = reviewRepository.save(review);

        return ReviewDto.from(savedReview);
    }

    public ReviewDto getReview(final Long id) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));
        if (review.getIsDeleted()) {
            throw new NotFoundReviewException(id);
        }

        return ReviewDto.from(review);
    }

    public List<ReviewDto> getAllReviewsByMemberId(final Long memberId) {
        final List<Review> reviews = reviewRepository.findAllByMemberId(memberId);

        return CustomListUtils.mapTo(reviews, ReviewDto::from);
    }

    public List<ReviewDto> getAllReviewsByPlaceId(final Long placeId) {
        final List<Review> reviews = reviewRepository.findAllByPlaceId(placeId);

        return CustomListUtils.mapTo(reviews, ReviewDto::from);
    }

    @Transactional
    public ReviewDto updateReview(final Long id, final ReviewUpdateCommand reviewUpdateCommand) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));
        final Map<Long, ReviewTag> updateReviewTags
                = reviewTagRepository.findByPerId(reviewUpdateCommand.getReviewTagIds());
        final Review target = reviewUpdateCommand.toEntity(updateReviewTags);

        review.update(target);

        return ReviewDto.from(review);
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

        List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

        for (Review review : reviews) {
            reviewImageDtos.addAll(getReviewImageDtos(review, members, images));
        }

        return reviewImageDtos;
    }

    private Member getMappedMember(final Review review, final Map<Long, Member> members) {
        Long memberId = review.getMemberId();

        if (!members.containsKey(memberId)) {
            // 가드 로직: 멤버 ID가 멤버 맵에 존재하지 않으면 null 반환 또는 예외 처리 등을 수행할 수 있습니다.
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
            reviewImageDtos.add(ReviewImageDto.of(mappedMember, mappedImage));
        }

        return reviewImageDtos;
    }
}
