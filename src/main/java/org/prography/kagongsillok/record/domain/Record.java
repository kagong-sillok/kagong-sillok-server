package org.prography.kagongsillok.record.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.auditing.AuditingTimeEntity;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;

@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long placeId;

    private String duration;

    private String description;

    private String imageIds;

    @Builder
    public Record(
            final Long memberId,
            final Long placeId,
            final String duration,
            final String description,
            final List<Long> imageIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.duration = duration;
        this.description = description;
    }

    public List<Long> getImageIds() {
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }
}
