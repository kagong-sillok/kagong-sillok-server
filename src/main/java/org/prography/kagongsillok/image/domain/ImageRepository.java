package org.prography.kagongsillok.image.domain;

import java.util.List;
import org.prography.kagongsillok.image.infrastructure.ImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

    List<Image> findByIdIn(List<Long> imageIds);
}
