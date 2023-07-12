package org.prography.kagongsillok.record.domain;

import org.prography.kagongsillok.record.infrastructure.RecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {

}
