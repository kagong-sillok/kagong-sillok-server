package org.prography.kagongsillok.place.application;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.application.exception.NotFoundImageException;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.member.domain.dto.PlaceSurfaceInfo;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceLocationAroundSearchCondition;
import org.prography.kagongsillok.place.application.dto.PlaceSearchCondition;
import org.prography.kagongsillok.place.application.dto.PlaceSurfaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Timed("timer.place")
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public PlaceDto createPlace(final PlaceCreateCommand placeCreateCommand) {
        checkExistImage(placeCreateCommand.getImageIds());
        final Place savedPlace = placeRepository.save(placeCreateCommand.toEntity());

        return PlaceDto.of(savedPlace, getImages(savedPlace));
    }

    @Counted("counter.place")
    public PlaceDto getPlace(final Long id) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        return PlaceDto.of(place, getImages(place));
    }

    @Counted("counter.place")
    public PlaceDto getPlaceWithTags(final Long placeId) {
        final Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundPlaceException(placeId));

        final List<Review> reviews = reviewRepository.findAllByPlaceId(placeId);

        return PlaceDto.of(
                place,
                getImages(place),
                getReviewTagsRelatedToPlace(reviews),
                calculateRatingAvg(reviews)
        );
    }

    @Counted("counter.place")
    public List<PlaceDto> searchPlacesLocationAround(final PlaceLocationAroundSearchCondition searchCondition) {
        final List<Place> places = placeRepository.findByLocationAround(
                Location.of(searchCondition.getLatitude(), searchCondition.getLongitude()),
                searchCondition.getLatitudeBound(),
                searchCondition.getLongitudeBound()
        );

        return getPlaceDtos(places);
    }

    @Counted("counter.place")
    public List<PlaceSurfaceDto> searchPlaces(final PlaceSearchCondition searchCondition) {
        final List<PlaceSurfaceInfo> places = placeRepository.searchPlace(
                searchCondition.getName(),
                Location.of(searchCondition.getLatitude(), searchCondition.getLongitude()),
                searchCondition.getLatitudeBound(),
                searchCondition.getLongitudeBound()
        );

        return CustomListUtils.mapTo(places, PlaceSurfaceDto::from);
    }

    @Transactional
    public PlaceDto updatePlace(final Long id, final PlaceUpdateCommand updateCommand) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        checkExistImage(updateCommand.getImageIds());

        final Place target = updateCommand.toEntity();
        place.update(target);

        return PlaceDto.of(place, getImages(place));
    }

    @Transactional
    public void deletePlace(final Long id) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        place.delete();
    }

    @Counted("counter.place")
    public List<PlaceDto> searchPlacesByTags(final List<Long> reviewTagIds) {
        final List<Review> reviews = reviewRepository.findByReviewTagIds(reviewTagIds);
        final List<Long> placeIds = reviews.stream()
                .map(Review::getPlaceId)
                .toList();

        final Map<Long, List<Review>> placeIdReviewsMap = reviews.stream()
                .collect(Collectors.groupingBy(Review::getPlaceId));

        final List<Place> places = placeRepository.findByIdIn(placeIds);

        return createPlaceDtos(places, placeIdReviewsMap);
    }

    private List<PlaceDto> getPlaceDtos(final List<Place> places) {
        final List<Long> placeIds = places.stream()
                .map(Place::getId)
                .collect(Collectors.toList());

        final List<Review> reviews = reviewRepository.findByPlaceIds(placeIds);

        final Map<Long, List<Review>> placeIdReviewsMap = reviews.stream()
                .collect(Collectors.groupingBy(Review::getPlaceId));

        return createPlaceDtos(places, placeIdReviewsMap);
    }

    private List<PlaceDto> createPlaceDtos(final List<Place> places, final Map<Long, List<Review>> reviewsMap) {
        return places.stream()
                .map(place -> {
                    List<Review> relatedReviews = reviewsMap.getOrDefault(place.getId(), List.of());

                    return PlaceDto.of(
                            place,
                            getImages(place),
                            getReviewTagsRelatedToPlace(relatedReviews),
                            calculateRatingAvg(relatedReviews)
                    );
                })
                .collect(Collectors.toList());
    }

    private List<ReviewTag> getReviewTagsRelatedToPlace(final List<Review> reviews) {
        return reviews.stream()
                .flatMap(review -> review.getTagMappings()
                        .getValues()
                        .stream()
                        .map(ReviewTagMapping::getReviewTag)
                )
                .collect(Collectors.toList());
    }

    private void checkExistImage(final List<Long> imageIds) {
        if (imageRepository.isNotExistIdIn(imageIds)) {
            throw new NotFoundImageException(imageIds);
        }
    }

    private Double calculateRatingAvg(final List<Review> reviews) {
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .sum() / reviews.size();
    }

    private List<Image> getImages(final Place place) {
        return imageRepository.findByIdIn(place.getImageIds());
    }
}
