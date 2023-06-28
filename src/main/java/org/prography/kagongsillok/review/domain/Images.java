package org.prography.kagongsillok.review.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.review.domain.exception.InvalidNumberOfImagesException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Images {

    public static final int MAX_NUMBER_OF_IMAGE = 5;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", nullable = false, updatable = false)
    private List<Image> images = new ArrayList<>();

    private Images(final List<Image> images) {
        this.images = images;
    }

    public static Images of(final List<Image> images) {
        if (Objects.isNull(images)) {
            return new Images();
        }
        validateNumberOfImages(images);
        return new Images(images);
    }

    private static void validateNumberOfImages(final List<Image> images) {
        if (images.size() > MAX_NUMBER_OF_IMAGE) {
            throw new InvalidNumberOfImagesException(images.size());
        }
    }

}
