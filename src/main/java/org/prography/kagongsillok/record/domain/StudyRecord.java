package org.prography.kagongsillok.record.domain;

import java.time.LocalDate;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;
import org.prography.kagongsillok.record.domain.vo.StudyRecordDescription;
import org.prography.kagongsillok.record.domain.vo.StudyRecordDuration;
import org.prography.kagongsillok.record.domain.vo.StudyRecordStudyDate;

@Getter
@Entity
@Table(name = "study_record")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update study_record set is_deleted = true, updated_at = now() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecord extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long placeId;

    private String placeName;

    @Embedded
    private StudyRecordStudyDate studyDate;

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
            final int studyYear,
            final int studyMonth,
            final int studyDay,
            final int duration,
            final String description,
            final List<Long> imageIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.studyDate = StudyRecordStudyDate.of(studyYear, studyMonth, studyDay);
        this.imageIds = CustomListUtils.joiningToString(imageIds, ",");
        this.duration = StudyRecordDuration.from(duration);
        this.description = StudyRecordDescription.from(description);
        this.writtenAt = ZonedDateTime.now();
    }

    public List<Long> getImageIds() {
        return CustomStringUtils.splitToList(imageIds, ",", Long::valueOf);
    }

    public LocalDate getStudyDate() {
        return studyDate.getValue();
    }

    public int getDuration() {
        return duration.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

}
