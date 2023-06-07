package org.prography.kagongsillok.image.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.application.dto.ImageDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageListResponse {

    public List<ImageResponse> images;

    public static ImageListResponse of(final List<ImageDto> imageDtos) {
        return new ImageListResponse(CustomListUtils.mapTo(imageDtos, ImageResponse::from));
    }
}
