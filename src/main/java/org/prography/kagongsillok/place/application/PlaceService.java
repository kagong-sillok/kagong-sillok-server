package org.prography.kagongsillok.place.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.image.application.exception.NotFoundImageException;
import org.prography.kagongsillok.image.domain.ImageRepository;
import org.prography.kagongsillok.place.application.dto.PlaceCreateCommand;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceLocationAroundSearchCondition;
import org.prography.kagongsillok.place.application.dto.PlaceUpdateCommand;
import org.prography.kagongsillok.place.application.exception.NotFoundPlaceException;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewRepository;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public PlaceDto createPlace(final PlaceCreateCommand placeCreateCommand) {
        final Place savedPlace = placeRepository.save(placeCreateCommand.toEntity());
        checkExistImage(placeCreateCommand.getImageIds());

        return PlaceDto.from(savedPlace);
    }

    public PlaceDto getPlace(final Long id) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));
        if (place.getIsDeleted()) {
            throw new NotFoundPlaceException(id);
        }

        return PlaceDto.from(place);
    }

    public PlaceDto getPlaceWithTags(final Long placeId) {
        final Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundPlaceException(placeId));
        if (place.getIsDeleted()) {
            throw new NotFoundPlaceException(placeId);
        }
        final List<Review> reviews = reviewRepository.findAllByPlaceId(placeId);

        return PlaceDto.of(place, getReviewTagsRelatedToPlace(reviews));
    }

    public List<PlaceDto> searchPlacesLocationAround(final PlaceLocationAroundSearchCondition searchCondition) {
        final List<Place> places = placeRepository.findByLocationAround(
                Location.of(searchCondition.getLatitude(), searchCondition.getLongitude()),
                searchCondition.getLatitudeBound(),
                searchCondition.getLongitudeBound()
        );

        return getPlaceDtos(places);
    }

    public List<PlaceDto> searchPlacesByName(final String name) {
        final List<Place> places = placeRepository.findByNameContains(name);

        return getPlaceDtos(places);
    }

    @Transactional
    public PlaceDto updatePlace(final Long id, final PlaceUpdateCommand updateCommand) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        checkExistImage(updateCommand.getImageIds());

        final Place target = updateCommand.toEntity();
        place.update(target);

        return PlaceDto.from(place);
    }

    @Transactional
    public void deletePlace(final Long id) {
        final Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaceException(id));

        place.delete();
    }

    public List<PlaceDto> searchPlacesByTags(final List<Long> reviewTagIds) {
        final List<Review> reviews = reviewRepository.findByReviewTagIds(reviewTagIds);
        final List<Long> placeIds = reviews.stream()
                .map(review -> review.getPlaceId())
                .collect(Collectors.toSet())
                .stream()
                .toList();

        final List<Place> places = placeRepository.findByIdIn(placeIds);

        return createPlaceDtos(places, reviews);
    }
    private List<PlaceDto> getPlaceDtos(final List<Place> places) {
        final List<Long> placeIds = places.stream()
                .map(place -> place.getId())
                .collect(Collectors.toList());
        final List<Review> reviews = reviewRepository.findByPlaceIds(placeIds);

        return createPlaceDtos(places, reviews);
    }

    private List<PlaceDto> createPlaceDtos(final List<Place> places, final List<Review> reviews) {
        return places.stream()
                .map(place -> {
                    List<Review> relatedReviews = reviews.stream()
                            .filter(review -> review.getPlaceId().equals(place.getId()))
                            .collect(Collectors.toList());
                    return PlaceDto.of(place, getReviewTagsRelatedToPlace(relatedReviews));
                })
                .collect(Collectors.toList());
    }

    private List<ReviewTag> getReviewTagsRelatedToPlace(final List<Review> reviews) {
        return reviews.stream()
                .flatMap(review -> review.getTagMappings()
                        .getValues()
                        .stream()
                        .map(reviewTagMapping -> reviewTagMapping.getReviewTag())
                )
                .collect(Collectors.toList());
    }

    private void checkExistImage(final List<Long> imageIds) {
        if (imageRepository.isExistIdIn(imageIds)) {
            throw new NotFoundImageException(imageIds);
        }
    }
}
