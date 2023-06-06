package org.prography.kagongsillok.image.application;

import org.prography.kagongsillok.image.application.dto.ImageCreateCommand;

public class ImageServiceTestFixture {

    public static ImageCreateCommand squareSize100jpegImageCreateCommand(final String url) {
        return ImageCreateCommand.builder()
                .url(url)
                .width(100)
                .height(100)
                .extension("jpeg")
                .build();
    }
}
