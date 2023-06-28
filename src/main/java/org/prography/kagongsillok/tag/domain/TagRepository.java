package org.prography.kagongsillok.tag.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByIdIn(List<Long> tagIds);
}
