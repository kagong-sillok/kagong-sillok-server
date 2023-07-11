package org.prography.kagongsillok.image.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private Integer width;
    private Integer height;
    private String extension;

    @Builder
    public Image(final String url, final Integer width, final Integer height, final String extension) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.extension = extension;
    }
}
