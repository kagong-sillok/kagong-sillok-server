package org.prography.kagongsillok.record.domain;

import org.prography.kagongsillok.record.infrastructure.StudyRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long>, StudyRecordRepositoryCustom {

}
