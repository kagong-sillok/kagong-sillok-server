package org.prography.kagongsillok.image.application.dto;

import lombok.Builder;
import org.prography.kagongsillok.image.domain.Image;

public record ImageDto(
        Long id,
        String url,
        Integer width,
        Integer height,
        String extension
) {

    @Builder
    public ImageDto {
    }

    public static ImageDto from(final Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .url(image.getUrl())
                .width(image.getWidth())
                .height(image.getHeight())
                .extension(image.getExtension())
                .build();
    }
}
