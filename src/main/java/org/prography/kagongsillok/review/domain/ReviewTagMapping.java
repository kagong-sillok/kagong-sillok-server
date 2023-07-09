package org.prography.kagongsillok.review.domain;


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

@Getter
@Entity
@Table(name = "review_tag_mapping")
@NoArgsConstructor
public class ReviewTagMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tag_id")
    private ReviewTag reviewTag;

    public ReviewTagMapping(final ReviewTag reviewTag) {
        this.reviewTag = reviewTag;
    }
}
