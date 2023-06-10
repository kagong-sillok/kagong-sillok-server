package org.prography.kagongsillok.image.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByIdIn(List<Long> imageIds);
}
