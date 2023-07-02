package org.prography.kagongsillok.ReviewTag.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.tag.domain.Tag;

@Getter
@Entity
@Table(name = "review_tag")
@NoArgsConstructor
public class ReviewTagMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public ReviewTagMapping(final Review review, final Tag tag) {
        setReview(review);
        setTag(tag);
    }

    private void setReview(Review review) {
        review.addReviewTagMapping(this);
    }

    private void setTag(Tag tag) {
        this.tag = tag;
    }

    public void disconnect(Review review) {
        disconnectReview(review);
        disconnectTag();
    }

    private void disconnectReview(Review review) {
        review.getTags().getReviewTagMappings().remove(this);
    }

    private void disconnectTag() {
        this.tag = null;
    }
}
