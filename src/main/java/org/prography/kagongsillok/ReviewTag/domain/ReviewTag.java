package org.prography.kagongsillok.ReviewTag.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.tag.domain.Tag;

@Getter
@Entity
@Table(name = "review_tag")
@NoArgsConstructor
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public void setReviewAndTag(final Review review, final Tag tag) {
        setReview(review);
        setTag(tag);
    }

    private void setReview(Review review) {
        this.review = review;

        review.getTags().getReviewTags().add(this);
    }

    private void setTag(Tag tag) {
        this.tag = tag;

        tag.getReviews().getReviewTags().add(this);
    }

    public void disconnect() {
        disconnectReview();
        disconnectTag();
    }

    private void disconnectReview() {
        review.getTags().getReviewTags().remove(this);

        this.review = null;
    }

    private void disconnectTag() {
        tag.getReviews().getReviewTags().remove(this);

        this.tag = null;
    }
}
