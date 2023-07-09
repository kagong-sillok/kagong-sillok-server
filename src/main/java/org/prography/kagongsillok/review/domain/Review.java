package org.prography.kagongsillok.review.domain;


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
import org.prography.kagongsillok.common.auditing.AuditingTimeEntity;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.review.domain.vo.ReviewContent;
import org.prography.kagongsillok.review.domain.vo.ReviewRating;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long placeId;

    @Embedded
    private ReviewRating rating;

    @Embedded
    private ReviewContent content;

    private String imageIds;

    @Embedded
    private ReviewTagMappings tagMappings;

    private Boolean isDeleted = Boolean.FALSE;

    @Builder
    public Review(
            final Long memberId,
            final Long placeId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<ReviewTagMapping> tagMappings) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.rating = ReviewRating.from(rating);
        this.content = ReviewContent.from(content);
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.tagMappings = ReviewTagMappings.of(tagMappings);
    }

    public void update(final Review target) {
        this.rating = target.rating;
        this.content = target.content;
        this.imageIds = target.imageIds;
        this.tagMappings.update(target.tagMappings);
    }

    public List<Long> getImageIds() {
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }

    public void delete() {
        this.isDeleted = true;
    }

    public int getRating() {
        return rating.getValue();
    }

    public String getContent() {
        return content.getValue();
    }
}
