package org.prography.kagongsillok.image.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageCreateCommand {

    private String url;
    private Integer width;
    private Integer height;
    private String extension;

    @Builder
    public ImageCreateCommand(final String url, final Integer width, final Integer height, final String extension) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.extension = extension;
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
