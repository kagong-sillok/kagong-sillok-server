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
        review.addReviewTagMapping(this);
        this.tag = tag;
    }

    public void disconnectTag() {
        this.tag = null;
    }
}
