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
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Links;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {

    @ElementCollection
    @CollectionTable(
            name = "review_tags",
            joinColumns = @JoinColumn(name = "review_id")
    )
    private List<String> tags = new ArrayList<>();

    private Tags(final List<String> tags) {
        this.tags = tags;
    }

    public static Tags of(final List<String> tags) {
        if (Objects.isNull(tags)) {
            return new Tags();
        }
        return new Tags(tags);
    }
}
