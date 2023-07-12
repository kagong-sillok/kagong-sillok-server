package org.prography.kagongsillok.review.domain;

import org.prography.kagongsillok.review.infrastructure.ReviewTagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long>, ReviewTagRepositoryCustom {

}
