package org.prography.kagongsillok.place.domain;

import java.util.Arrays;
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
            final Location location,
            final String imageIds,
            final String phone,
            final Links links,
            final BusinessHours businessHours
    ) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.imageIds = imageIds;
        this.phone = phone;
        this.links = links;
        this.businessHours = businessHours;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public List<Long> getImageIds() {
        return Arrays.stream(imageIds.split(","))
                .map(Long::valueOf)
                .toList();
    }
}
