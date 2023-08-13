package org.prography.kagongsillok.review.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.member.application.exception.NotFoundMemberException;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewDto;
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

    @Transactional
    public ReviewDto createReview(final ReviewCreateCommand reviewCreateCommand) {
        final Map<Long, ReviewTag> reviewTagIds
                = reviewTagRepository.findByPerId(reviewCreateCommand.getReviewTagIds());

        final Long memberId = reviewCreateCommand.getMemberId();

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        final Review review = reviewCreateCommand.toEntity(member.getNickname(), reviewTagIds);

        final Review savedReview = reviewRepository.save(review);

        return ReviewDto.of(savedReview, member);
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
        if (member.getIsDeleted()) {
            throw new NotFoundReviewException(memberId);
        }

        return ReviewDto.of(review, member);
    }

    public List<ReviewDto> getAllReviewsByMemberId(final Long memberId) {
        final List<Review> reviews = reviewRepository.findAllByMemberId(memberId);

        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            final Member member = memberRepository.findById(review.getMemberId())
                    .orElseThrow(() -> new NotFoundMemberException(review.getMemberId()));
            reviewDtos.add(ReviewDto.of(review, member));
        }

        return reviewDtos;
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
        final Review target = reviewUpdateCommand.toEntity(updateReviewTags);
        review.update(target);

        final Member member = memberRepository.findById(target.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException(target.getMemberId()));

        return ReviewDto.of(review, member);
    }

    @Transactional
    public void deleteReview(final Long id) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundReviewException(id));

        review.delete();
    }

    private List<Long> getReviewedMemberIds(final List<Review> reviews) {
        return reviews.stream()
                .map(review -> review.getMemberId())
                .collect(Collectors.toList());
    }

    private List<ReviewDto> mappingMemberToReview(final List<Review> reviews, final Map<Long, Member> members) {
        return reviews.stream()
                .map(review -> {
                    Member member = members.get(review.getMemberId());
                    if (member == null) {
                        member = Member.builder()
                                .nickname("알 수 없음")
                                .email("Unknown@unknown.com")
                                .build();
                    }

                    return ReviewDto.of(review, member);
                })
                .collect(Collectors.toList());
    }
}
