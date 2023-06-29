package org.prography.kagongsillok.review.domain;


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
import org.prography.kagongsillok.tag.domain.Tag;

@Getter
@Entity
@Table(name = "review_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public ReviewTag(final Review review, final Tag tag) {
        setReview(review);
        setTag(tag);
    }

    private void setReview(Review review) {
        
    }

    private void setTag(Tag tag) {

    }
}
