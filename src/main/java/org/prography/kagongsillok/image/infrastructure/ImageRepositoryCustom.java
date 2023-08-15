package org.prography.kagongsillok.image.infrastructure;

import java.util.List;

public interface ImageRepositoryCustom {

    boolean isExistIdIn(final List<Long> imageIds);

}
