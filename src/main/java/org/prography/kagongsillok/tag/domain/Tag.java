package org.prography.kagongsillok.tag.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private boolean isDeleted = Boolean.FALSE;

    public Tag(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }
}
