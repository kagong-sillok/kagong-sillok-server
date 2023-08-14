package org.prography.kagongsillok.review.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;

@Getter
@Entity
@Table(name = "review_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewTag extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    private String tagContent;

    public ReviewTag(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }
}
