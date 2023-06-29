package org.prography.kagongsillok.tag.infrastructure;

import java.util.List;
import org.prography.kagongsillok.tag.domain.Tag;

public interface TagRepositoryCustom {

    List<Tag> findAllTags();

}
