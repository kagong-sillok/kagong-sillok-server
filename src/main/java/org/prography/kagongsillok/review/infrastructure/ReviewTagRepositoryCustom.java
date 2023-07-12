package org.prography.kagongsillok.review.infrastructure;

import java.util.List;
import java.util.Map;
import org.prography.kagongsillok.review.domain.ReviewTag;

public interface ReviewTagRepositoryCustom {

    List<ReviewTag> findAllTags();

    List<ReviewTag> findByIdIn(List<Long> tagIds);

    Map<Long, ReviewTag> findByPerId(List<Long> tagIds);
}
