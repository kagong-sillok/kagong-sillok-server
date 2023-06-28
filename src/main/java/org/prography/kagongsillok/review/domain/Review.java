package org.prography.kagongsillok.review.domain;


import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.auditing.AuditingTimeEntity;
import org.prography.kagongsillok.place.domain.Place;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private int rating;

    private String content;

    @Embedded
    private Tags tags;

    @Embedded
    private Images images;

    private Boolean isDeleted = false;

    @Builder
    public Review(
            final Long memberId,
            final int rating,
            final String content,
            final Tags tags,
            final Images images) {
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.images = images;
    }

    public void update(final Review target) {
        this.rating = target.rating;
        this.content = target.content;
        this.tags = target.tags;
        this.images = target.images;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
