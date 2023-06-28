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
import org.prography.kagongsillok.tag.domain.Tag;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tag_id", nullable = false, updatable = false)
    private List<Tag> tags = new ArrayList<>();

    private Tags(final List<Tag> tags) {
        this.tags = tags;
    }

    public static Tags of(final List<Tag> tags) {
        if (Objects.isNull(tags)) {
            return new Tags();
        }
        return new Tags(tags);
    }
}
