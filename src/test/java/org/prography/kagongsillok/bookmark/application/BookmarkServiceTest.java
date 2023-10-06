package org.prography.kagongsillok.bookmark.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.bookmark.domain.Bookmark;
import org.prography.kagongsillok.bookmark.domain.BookmarkRepository;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.BusinessHourCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand.LinkCreateCommand;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookmarkServiceTest {

    private final List<LinkCreateCommand> linkCreateCommands = List.of(
            new LinkCreateCommand(LinkType.INSTAGRAM.name(), "testInstagramUrl"),
            new LinkCreateCommand(LinkType.BLOG.name(), "testBlogUrl"),
            new LinkCreateCommand(LinkType.WEB.name(), "testWebUrl")
    );
    private final List<BusinessHourCreateCommand> businessHourCreateCommands = List.of(
            new BusinessHourCreateCommand(
                    DayOfWeek.MONDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            ),
            new BusinessHourCreateCommand(
                    DayOfWeek.TUESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            ),
            new BusinessHourCreateCommand(
                    DayOfWeek.WEDNESDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            ),
            new BusinessHourCreateCommand(
                    DayOfWeek.THURSDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            ),
            new BusinessHourCreateCommand(
                    DayOfWeek.FRIDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            ),
            new BusinessHourCreateCommand(
                    DayOfWeek.SATURDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            ),
            new BusinessHourCreateCommand(
                    DayOfWeek.SUNDAY.name(), LocalTime.of(12, 0), LocalTime.of(23, 59)
            )
    );

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private PlaceRepository placeRepository;
    private Place testPlace;

    @BeforeEach
    void setUp() {
        final PlaceCreateCommand placeCreateCommand1 = PlaceCreateCommand
                .builder()
                .name("테스트 장소1")
                .address("테스트특별시 테스트구")
                .latitude(49.67)
                .longitude(129.23)
                .imageIds(List.of(1L, 2L, 3L))
                .phone("testPhoneNumber")
                .links(linkCreateCommands)
                .businessHours(businessHourCreateCommands)
                .build();
        testPlace = placeRepository.save(placeCreateCommand1.toEntity());
    }

    @Test
    void 장소를_북마크한다() {
        bookmarkService.toggle(testPlace.getId(), 123L);

        final Optional<Bookmark> bookmark = bookmarkRepository.findByPlaceIdAndMemberIdAndIsDeletedFalse(testPlace.getId(), 123L);

        assertThat(bookmark).isPresent();
    }

    @Test
    void 장소_북마크를_해제한다() {
        bookmarkService.toggle(testPlace.getId(), 123L);

        bookmarkService.toggle(testPlace.getId(), 123L);

        final Optional<Bookmark> bookmark = bookmarkRepository.findByPlaceIdAndMemberIdAndIsDeletedFalse(testPlace.getId(), 123L);
        assertThat(bookmark).isEmpty();
    }

    @Test
    void 장소_북마크를_해제하고_다시_북마크_한다() {
        bookmarkService.toggle(testPlace.getId(), 123L);
        bookmarkService.toggle(testPlace.getId(), 123L);

        bookmarkService.toggle(testPlace.getId(), 123L);
        final Optional<Bookmark> bookmark = bookmarkRepository.findByPlaceIdAndMemberIdAndIsDeletedFalse(testPlace.getId(), 123L);
        assertThat(bookmark).isPresent();
    }

    @Nested
    class 동시성_테스트 {

        @Test
        void 동시에_북마크_홀수번_토글() throws InterruptedException {
            final int count = 21;
            final ExecutorService executorService = Executors.newFixedThreadPool(count);

            final CountDownLatch countDownLatch = new CountDownLatch(count);
            for (int i = 0; i < count; i++) {
                executorService.submit(() -> {
                    bookmarkService.toggle(testPlace.getId(), 123L);
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();

            final Optional<Bookmark> bookmark = bookmarkRepository.findByPlaceIdAndMemberIdAndIsDeletedFalse(testPlace.getId(), 123L);

            assertThat(bookmark).isPresent();
        }

        @Test
        void 동시에_북마크_짝수번_토글() throws InterruptedException {
            final int count = 20;
            final ExecutorService executorService = Executors.newFixedThreadPool(count);

            final CountDownLatch countDownLatch = new CountDownLatch(count);
            for (int i = 0; i < count; i++) {
                executorService.submit(() -> {
                    bookmarkService.toggle(testPlace.getId(), 123L);
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();

            final Optional<Bookmark> bookmark = bookmarkRepository.findByPlaceIdAndMemberIdAndIsDeletedFalse(testPlace.getId(), 123L);

            assertThat(bookmark).isEmpty();
        }
    }
}
