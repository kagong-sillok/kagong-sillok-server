package org.prography.kagongsillok.place.domain;

import org.prography.kagongsillok.place.infrastructure.PlaceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

}
