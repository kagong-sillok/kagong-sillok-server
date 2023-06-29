package org.prography.kagongsillok.tag.domain;

import java.util.ArrayList;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.ReviewTag.domain.ReviewTags;

@Getter
@Entity
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    private String tagContent;

    @Embedded
    private ReviewTags reviews;

    public Tag(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
        this.reviews = ReviewTags.of(new ArrayList<>());
    }
}
