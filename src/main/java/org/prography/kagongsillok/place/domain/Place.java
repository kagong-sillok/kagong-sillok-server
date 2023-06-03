package org.prography.kagongsillok.place.domain;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;

@Getter
@Entity
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @Embedded
    private Location location;

    private String imageIds; // 반정규화 컬럼 ex) 1,2,3
    private String phone;

    @Embedded
    private Links links;

    @Embedded
    private BusinessHours businessHours;

    private Boolean isDeleted;

    @Builder
    public Place(
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final List<Long> imageIds,
            final String phone,
            final List<Link> links,
            final List<BusinessHour> businessHours
    ) {
        this.name = name;
        this.address = address;
        this.location = Location.of(latitude, longitude);
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.phone = phone;
        this.links = Links.of(links);
        this.businessHours = BusinessHours.of(businessHours);
    }

    public void delete() {
        this.isDeleted = true;
    }

    public List<Long> getImageIds() {
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }

    public Double getLatitude() {
        return location.getLatitude();
    }

    public Double getLongitude() {
        return location.getLongitude();
    }
}
