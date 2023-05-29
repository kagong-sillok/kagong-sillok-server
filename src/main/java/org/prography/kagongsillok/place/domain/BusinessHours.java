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
public class BusinessHours {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    private List<BusinessHour> values = new ArrayList<>();

    private BusinessHours(final List<BusinessHour> values) {
        this.values = values;
    }

    public static BusinessHours of(final List<BusinessHour> businessHours) {
        if (Objects.isNull(businessHours)) {
            return new BusinessHours();
        }

        return new BusinessHours(businessHours);
    }
}
