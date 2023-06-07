package org.prography.kagongsillok.place.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "link")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LinkType linkType;
    private String url;

    public Link(final String linkType, final String url) {
        this.linkType = LinkType.valueOf(linkType);
        this.url = url;
    }
}
