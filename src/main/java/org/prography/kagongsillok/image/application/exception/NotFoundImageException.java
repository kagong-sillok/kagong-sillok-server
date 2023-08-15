package org.prography.kagongsillok.image.application.exception;

import java.util.List;
import org.prography.kagongsillok.common.exception.NotFoundException;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.utils.CustomStringUtils;

public final class NotFoundImageException extends NotFoundException {

    public NotFoundImageException(final List<Long> imageIds) {
        super(String.format("존재하지 않는 이미지 id가 포함되어 있습니다. imageIds = %s",
                CustomListUtils.joiningToString(imageIds, ",")));
    }
}
