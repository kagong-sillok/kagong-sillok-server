package org.prography.kagongsillok.review.domain;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Images {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "review_images",
            joinColumns = @JoinColumn(name = "review_id")
    )
    private Set<String> images = new LinkedHashSet<>();

    private Images(final Set<String> images) {
        this.images = images;
    }

    public static Images of(final Set<String> images) {
        if (Objects.isNull(images)) {
            return new Images();
        }
        return new Images(images);
    }

}
