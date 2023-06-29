package org.prography.kagongsillok.review.domain;


import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
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
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.review.domain.exception.InvalidContentLengthException;
import org.prography.kagongsillok.review.domain.exception.InvalidRatingBoundException;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AuditingTimeEntity {

    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static final int MIN_CONTENT_LENGTH = 1;
    private static final int MAX_CONTENT_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long placeId;

    private int rating;

    private String content;

    private String imageIds;

    @Embedded
    private Tags tags;

    private Boolean isDeleted = false;

    @Builder
    public Review(
            final Long memberId,
            final Long placeId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.rating = rating;
        this.content = content;
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.tagIds = CustomListUtils.joiningToString(tagIds, ",");
    }

    public void update(final Review target) {
        this.rating = target.rating;
        this.content = target.content;
        this.imageIds = target.imageIds;
        this.tagIds = target.tagIds;
    }

    public List<Long> getImageIds() {
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }

    public List<Long> getTagIds() {
        return CustomStringUtils.splitToList(tagIds, ",", Long::valueOf);
    }

    public void delete() {
        this.isDeleted = true;
    }
}
