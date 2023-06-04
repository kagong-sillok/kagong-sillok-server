package org.prography.kagongsillok.place.domain;

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

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Links {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    private List<Link> values = new ArrayList<>();

    private Links(final List<Link> values) {
        this.values = values;
    }

    public static Links of(final List<Link> links) {
        if (Objects.isNull(links)) {
            return new Links();
        }
        return new Links(links);
    }

    public void update(final Links newLinks) {
        values.clear();
        values.addAll(newLinks.getValues());
    }
}
