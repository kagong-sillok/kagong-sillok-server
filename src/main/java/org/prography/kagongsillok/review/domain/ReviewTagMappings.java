package org.prography.kagongsillok.review.domain;


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
public class ReviewTagMappings {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<ReviewTagMapping> values = new ArrayList<>();

    private ReviewTagMappings(final List<ReviewTagMapping> values) {
        this.values = values;
    }

    public static ReviewTagMappings of(final List<ReviewTagMapping> reviewTagMappings) {
        if (Objects.isNull(reviewTagMappings)) {
            return new ReviewTagMappings();
        }
        return new ReviewTagMappings(reviewTagMappings);
    }

    public void update(final ReviewTagMappings newTagMappings) {
        values.clear();
        values.addAll(newTagMappings.getValues());
    }
}
