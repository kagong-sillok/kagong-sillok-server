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
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.Links;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "review_tags",
            joinColumns = @JoinColumn(name = "review_id")
    )
    private Set<String> tags = new LinkedHashSet<>();

    private Tags(final Set<String> tags) {
        this.tags = tags;
    }

    public static Tags of(final Set<String> tags) {
        if (Objects.isNull(tags)) {
            return new Tags();
        }
        return new Tags(tags);
    }
}
