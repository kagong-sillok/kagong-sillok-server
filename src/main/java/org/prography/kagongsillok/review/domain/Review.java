package org.prography.kagongsillok.review.domain;


import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.review.domain.vo.ReviewContent;
import org.prography.kagongsillok.review.domain.vo.ReviewRating;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long placeId;

    private String memberNickName;

    @Embedded
    private ReviewRating rating;

    @Embedded
    private ReviewContent content;

    private String imageIds;

    @Embedded
    private ReviewTagMappings tagMappings;

    private ZonedDateTime writtenAt;

    @Builder
    public Review(
            final Long memberId,
            final Long placeId,
            final String memberNickName,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<ReviewTagMapping> tagMappings) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.memberNickName = memberNickName;
        this.rating = ReviewRating.from(rating);
        this.content = ReviewContent.from(content);
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.tagMappings = ReviewTagMappings.of(tagMappings);
        this.writtenAt = ZonedDateTime.now();
    }

    public void update(final Review target) {
        this.rating = target.rating;
        this.content = target.content;
        this.imageIds = target.imageIds;
        this.tagMappings.update(target.tagMappings);
    }

    public List<Long> getImageIds() {
        if (imageIds == null) {
            return List.of();
        }
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }

    public int getRating() {
        return rating.getValue();
    }

    public String getContent() {
        return content.getValue();
    }
}
