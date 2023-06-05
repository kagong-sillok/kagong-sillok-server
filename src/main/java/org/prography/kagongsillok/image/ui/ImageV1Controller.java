package org.prography.kagongsillok.image.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.image.application.ImageService;
import org.prography.kagongsillok.image.application.dto.ImageCreateCommand;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.image.ui.dto.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageV1Controller {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<CommonResponse<ImageResponse>> createImage(final ImageCreateRequest imageCreateRequest) {
        final ImageCreateCommand createCommand = imageCreateRequest.toCommand();
        final ImageDto imageDto = imageService.createImage(createCommand);

        return CommonResponse.success(ImageResponse.from(imageDto));
    }
}
