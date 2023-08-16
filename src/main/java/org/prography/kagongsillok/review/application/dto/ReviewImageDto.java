package org.prography.kagongsillok.review.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.review.domain.Review;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImageDto {

    private String imageUrl;
    private String memberName;
    private String memberProfileUrl;

    private ReviewImageDto(final Member member, final Image image) {
        this.imageUrl = image.getUrl();
        this.memberName = member.getNickname();
        this.memberProfileUrl = member.getProfileImageUrl();
    }

    public static ReviewImageDto of(Member member, Image image) {
        return new ReviewImageDto(member, image);
    }
}
