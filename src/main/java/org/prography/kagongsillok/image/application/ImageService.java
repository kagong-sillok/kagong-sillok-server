package org.prography.kagongsillok.image.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.image.application.dto.ImageCreateCommand;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public ImageDto createImage(final ImageCreateCommand createCommand) {
        final Image image = createCommand.toEntity();
        final Image savedImage = imageRepository.save(image);

        return ImageDto.from(savedImage);
    }
}
