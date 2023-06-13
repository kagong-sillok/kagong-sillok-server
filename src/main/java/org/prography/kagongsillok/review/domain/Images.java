package org.prography.kagongsillok.review.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Images {

    @ElementCollection
    @CollectionTable(
            name = "review_images",
            joinColumns = @JoinColumn(name = "review_id")
    )
    private List<String> images = new ArrayList<>();

    private Images(final List<String> images) {
        this.images = images;
    }

    public static Images of(final List<String> images) {
        if (Objects.isNull(images)) {
            return new Images();
        }
        return new Images(images);
    }

}
