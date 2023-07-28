package org.prography.kagongsillok.image.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prography.kagongsillok.image.application.ImageServiceTestFixture.squareSize100jpegImageCreateCommand;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.image.application.dto.ImageCreateCommand;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    void 이미지를_생성한다() {
        final ImageCreateCommand imageCreateCommand = ImageCreateCommand.builder()
                .url("testImageUrl")
                .width(100)
                .height(100)
                .extension("jpeg")
                .build();

        final ImageDto imageDto = imageService.createImage(imageCreateCommand);

        assertAll(
                () -> assertThat(imageDto.getUrl()).isEqualTo("testImageUrl"),
                () -> assertThat(imageDto.getHeight()).isEqualTo(100),
                () -> assertThat(imageDto.getWidth()).isEqualTo(100),
                () -> assertThat(imageDto.getExtension()).isEqualTo("jpeg")
        );
    }

    @Test
    void 이미지를_여러개_생성한다() {
        final ImageCreateCommand imageCreateCommand1 = ImageCreateCommand.builder()
                .url("testImageUrl1")
                .width(100)
                .height(100)
                .extension("jpeg")
                .build();
        final ImageCreateCommand imageCreateCommand2 = ImageCreateCommand.builder()
                .url("testImageUrl2")
                .width(200)
                .height(200)
                .extension("jpeg")
                .build();
        final ImageCreateCommand imageCreateCommand3 = ImageCreateCommand.builder()
                .url("testImageUrl3")
                .width(300)
                .height(300)
                .extension("jpeg")
                .build();
        final List<ImageCreateCommand> imageCreateCommands = new ArrayList<>();
        imageCreateCommands.add(imageCreateCommand1);
        imageCreateCommands.add(imageCreateCommand2);
        imageCreateCommands.add(imageCreateCommand3);

        final List<ImageDto> imageDtos = imageService.createImages(imageCreateCommands);

        assertAll(
                () -> assertThat(imageDtos.size()).isEqualTo(3),
                () -> assertThat(imageDtos).extracting("url")
                        .containsAll(List.of("testImageUrl1", "testImageUrl2", "testImageUrl3")),
                () -> assertThat(imageDtos).extracting("width")
                        .containsAll(List.of(100, 200, 300)),
                () -> assertThat(imageDtos).extracting("height")
                        .containsAll(List.of(100, 200, 300))
        );
    }

    @Test
    void 아이디로_이미지를_여러_건_조회한다() {
        final ImageCreateCommand imageCreateCommand1 = squareSize100jpegImageCreateCommand("testImageUrl1");
        final ImageCreateCommand imageCreateCommand2 = squareSize100jpegImageCreateCommand("testImageUrl2");
        final ImageCreateCommand imageCreateCommand3 = squareSize100jpegImageCreateCommand("testImageUrl3");
        final Long image1Id = imageService.createImage(imageCreateCommand1).getId();
        final Long image2Id = imageService.createImage(imageCreateCommand2).getId();
        final Long image3Id = imageService.createImage(imageCreateCommand3).getId();

        final List<ImageDto> imageDtos = imageService.getImages(List.of(image1Id, image2Id, image3Id));

        assertAll(
                () -> assertThat(imageDtos).hasSize(3),
                () -> assertThat(imageDtos).extracting("url")
                        .containsAll(List.of("testImageUrl1", "testImageUrl2", "testImageUrl3"))
        );
    }

    @Test
    void 아이디로_이미지를_여러_건_조회할_때_없는_이미지_id는_무시하고_있는_것만_응답한다() {
        final ImageCreateCommand imageCreateCommand1 = squareSize100jpegImageCreateCommand("testImageUrl1");
        final ImageCreateCommand imageCreateCommand2 = squareSize100jpegImageCreateCommand("testImageUrl2");
        final ImageCreateCommand imageCreateCommand3 = squareSize100jpegImageCreateCommand("testImageUrl3");
        final Long image1Id = imageService.createImage(imageCreateCommand1).getId();
        final Long image2Id = imageService.createImage(imageCreateCommand2).getId();
        final Long image3Id = imageService.createImage(imageCreateCommand3).getId();

        final List<ImageDto> imageDtos = imageService.getImages(
                List.of(image1Id, image2Id, image3Id, 999999L, 999998L));

        assertAll(
                () -> assertThat(imageDtos).hasSize(3),
                () -> assertThat(imageDtos).extracting("url")
                        .containsAll(List.of("testImageUrl1", "testImageUrl2", "testImageUrl3"))
        );
    }
}
