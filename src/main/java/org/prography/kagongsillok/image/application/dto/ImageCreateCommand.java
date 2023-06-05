package org.prography.kagongsillok.image.application.dto;

import lombok.Builder;
import org.prography.kagongsillok.image.domain.Image;

public record ImageCreateCommand(
        String url,
        Integer width,
        Integer height,
        String extension
) {

    @Builder
    public ImageCreateCommand {
    }

    public Image toEntity() {
        return Image.builder()
                .url(url)
                .width(width)
                .height(height)
                .extension(extension)
                .build();
    }
}
