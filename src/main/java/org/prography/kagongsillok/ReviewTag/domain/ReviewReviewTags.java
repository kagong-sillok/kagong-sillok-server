package org.prography.kagongsillok.ReviewTag.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReviewTags {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_review_tags_id")
    private List<ReviewTagMapping> reviewTagMappings = new ArrayList<>();

    private ReviewReviewTags(final List<ReviewTagMapping> reviewTagMappings) {
        this.reviewTagMappings = reviewTagMappings;
    }

    public static ReviewReviewTags of(final List<ReviewTagMapping> reviewTagMappings) {
        if (Objects.isNull(reviewTagMappings)) {
            return new ReviewReviewTags();
        }
        return new ReviewReviewTags(reviewTagMappings);
    }
}
