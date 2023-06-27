package org.prography.kagongsillok.review.domain;

import org.prography.kagongsillok.review.infrastructure.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

}
