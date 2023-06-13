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

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AuditingTimeEntity {

    @Builder
    public Review(
            final Long userId,
            final int rating,
            final String content,
            final Tags tags,
            final Images images) {
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.images = images;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int rating;

    private String content;

    @Embedded
    private Tags tags;

    @Embedded
    private Images images;
}
