package org.prography.kagongsillok.image.infrastructure;

import java.util.List;
import java.util.Map;
import org.prography.kagongsillok.image.domain.Image;

public interface ImageRepositoryCustom {

    Map<Long, Image> findByIdInToMap(List<Long> imageIds);

    boolean isNotExistIdIn(final List<Long> imageIds);

    Map<Long, Image> getImageMap(List<Long> imageIds);
}
