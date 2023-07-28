package org.prography.kagongsillok.image.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
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

    @Transactional
    public List<ImageDto> createImages(final List<ImageCreateCommand> createCommands) {
        final List<Image> images = toImages(createCommands);
        final List<Image> savedImages = saveImages(images);

        return CustomListUtils.mapTo(savedImages, ImageDto::from);
    }

    public List<ImageDto> getImages(final List<Long> imageIds) {
        final List<Image> images = imageRepository.findByIdIn(imageIds);

        return CustomListUtils.mapTo(images, ImageDto::from);
    }

    private List<Image> toImages(List<ImageCreateCommand> commands) {
        return commands.stream().map(command -> command.toEntity()).collect(Collectors.toList());
    }

    private List<Image> saveImages(List<Image> images) {
        return images.stream().map(imageRepository::save).collect(Collectors.toList());
    }
}
