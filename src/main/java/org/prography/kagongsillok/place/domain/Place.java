package org.prography.kagongsillok.place.domain;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;
import org.prography.kagongsillok.common.exception.CanNotProceedException;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;

@Getter
@Entity
@Table(name = "place", indexes = {
        @Index(name = "ix__place__for_search", columnList = "latitude, longitude, name, phone, address, thumbnailImageUrl") // 커버링 인덱스
        //@Index(name = "ix__place__for_search2", columnList = "latitude, longitude, name") // index condition pushdown 위해 name 포함
})
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update place set is_deleted = true, updated_at = now() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @Embedded
    private Location location;
    private String thumbnailImageUrl;
    private String imageIds; // 반정규화 컬럼 ex) 1,2,3
    private String phone;

    @Embedded
    private Links links;

    @Embedded
    private BusinessHours businessHours;

    private Integer bookmarkCount;

    @Builder
    public Place(
            final String name,
            final String address,
            final Double latitude,
            final Double longitude,
            final String thumbnailImageUrl,
            final List<Long> imageIds,
            final String phone,
            final List<Link> links,
            final List<BusinessHour> businessHours
    ) {
        this.name = name;
        this.address = address;
        this.location = Location.of(latitude, longitude);
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.phone = phone;
        this.links = Links.of(links);
        this.businessHours = BusinessHours.of(businessHours);
        this.bookmarkCount = 0;
    }

    public void update(final Place target) {
        this.name = target.name;
        this.address = target.address;
        this.location = target.location;
        this.imageIds = target.imageIds;
        this.thumbnailImageUrl = target.thumbnailImageUrl;
        this.phone = target.phone;
        this.links.update(target.links);
        this.businessHours.update(target.businessHours);
    }

    public void increaseBookmarkCount() {
        bookmarkCount++;
    }

    public void decreaseBookmarkCount() {
        if (bookmarkCount == 0) {
            throw new CanNotProceedException("북마크 수가 0보다 낮을 수 없습니다.");
        }
        bookmarkCount--;
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
