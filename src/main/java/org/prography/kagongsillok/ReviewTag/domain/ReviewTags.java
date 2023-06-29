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
public class ReviewTags {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "review_tags_id", nullable = false)
    private List<ReviewTag> reviewTags = new ArrayList<>();

    private ReviewTags(final List<ReviewTag> reviewTags) {
        this.reviewTags = reviewTags;
    }

    public static ReviewTags of(final List<ReviewTag> reviewTags) {
        if (Objects.isNull(reviewTags)) {
            return new ReviewTags();
        }
        return new ReviewTags(reviewTags);
    }
}
