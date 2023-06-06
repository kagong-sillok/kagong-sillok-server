package org.prography.kagongsillok.image.ui.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.application.dto.ImageDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResponse {

    private Long id;
    private String url;
    private Integer width;
    private Integer height;
    private String extension;

    @Builder
    public ImageResponse(
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

    public static ImageResponse from(final ImageDto imageDto) {
        return ImageResponse.builder()
                .id(imageDto.getId())
                .url(imageDto.getUrl())
                .width(imageDto.getWidth())
                .height(imageDto.getHeight())
                .extension(imageDto.getExtension())
                .build();
    }
}
