package org.prography.kagongsillok.record.domain;

import java.time.ZonedDateTime;
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
import org.prography.kagongsillok.common.entity.AbstractRootEntity;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.record.domain.vo.StudyRecordDescription;
import org.prography.kagongsillok.record.domain.vo.StudyRecordDuration;

@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecord extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long placeId;

    private String placeName;

    @Embedded
    private StudyRecordDuration duration;

    @Embedded
    private StudyRecordDescription description;

    private String imageIds;

    private ZonedDateTime writtenAt;

    @Builder
    public StudyRecord(
            final Long memberId,
            final Long placeId,
            final String placeName,
            final String duration,
            final String description,
            final List<Long> imageIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.duration = StudyRecordDuration.from(duration);
        this.description = StudyRecordDescription.from(description);
        this.writtenAt = ZonedDateTime.now();
    }

    public List<Long> getImageIds() {
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }
}
