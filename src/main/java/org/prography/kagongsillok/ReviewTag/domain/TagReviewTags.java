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
public class TagReviewTags {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tag_review_tags_id")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    private TagReviewTags(final List<ReviewTag> reviewTags) {
        this.reviewTags = reviewTags;
    }

    public static TagReviewTags of(final List<ReviewTag> reviewTags) {
        if (Objects.isNull(reviewTags)) {
            return new TagReviewTags();
        }
        return new TagReviewTags(reviewTags);
    }
}
