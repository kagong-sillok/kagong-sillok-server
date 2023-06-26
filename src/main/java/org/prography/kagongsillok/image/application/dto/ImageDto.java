package org.prography.kagongsillok.image.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageDto {

    private Long id;
    private String url;
    private Integer width;
    private Integer height;
    private String extension;

    @Builder
    public ImageDto(
            final Long id,
            final String url,
            final Integer width,
            final Integer height,
            final String extension
    ) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
        this.extension = extension;
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
