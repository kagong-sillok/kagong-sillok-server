package org.prography.kagongsillok.image.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.image.application.ImageService;
import org.prography.kagongsillok.image.application.dto.ImageCreateCommand;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.ui.dto.ImageCreateRequest;
import org.prography.kagongsillok.image.ui.dto.ImageListResponse;
import org.prography.kagongsillok.image.ui.dto.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageV1Controller {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<CommonResponse<ImageResponse>> createImage(
            @RequestBody final ImageCreateRequest imageCreateRequest) {
        final ImageCreateCommand createCommand = imageCreateRequest.toCommand();
        final ImageDto imageDto = imageService.createImage(createCommand);

        return CommonResponse.success(ImageResponse.from(imageDto));
    }

    @PostMapping("/images")
    public ResponseEntity<CommonResponse<ImageListResponse>> createImages(
            @RequestBody final List<ImageCreateRequest> imageCreateRequests) {
        final List<ImageCreateCommand> createCommands = toCommands(imageCreateRequests);
        final List<ImageDto> imageDtos = imageService.createImages(createCommands);

        return CommonResponse.success(ImageListResponse.of(imageDtos));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<ImageListResponse>> getImages(@RequestParam final List<Long> imageIds) {
        final List<ImageDto> imageDtos = imageService.getImages(imageIds);

        return CommonResponse.success(ImageListResponse.of(imageDtos));
    }

    private List<ImageCreateCommand> toCommands(final List<ImageCreateRequest> requests) {
        return CustomListUtils.mapTo(requests, ImageCreateRequest::toCommand);
    }
}
