package org.prography.kagongsillok.tag.domain;

import org.prography.kagongsillok.tag.infrastructure.TagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {

}
