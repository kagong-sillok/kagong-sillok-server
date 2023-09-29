package org.prography.kagongsillok.place.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.place.application.PlaceService;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.application.dto.PlaceSearchCondition;
import org.prography.kagongsillok.place.ui.dto.PlaceListResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceLocationAroundSearchRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceV1Controller {

    private final PlaceService placeService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<PlaceResponse>> getPlace(@PathVariable("id") final Long id) {
        final PlaceDto placeDto = placeService.getPlace(id);
        return CommonResponse.success(PlaceResponse.from(placeDto));
    }

    @GetMapping("/with-tags/{placeId}")
    public ResponseEntity<CommonResponse<PlaceResponse>> getPlaceWithTags(@PathVariable("placeId") final Long placeId) {
        final PlaceDto placeDto = placeService.getPlaceWithTags(placeId);
        return CommonResponse.success(PlaceResponse.from(placeDto));
    }

    @GetMapping("/around")
    public ResponseEntity<CommonResponse<PlaceListResponse>> searchAroundPlace(
            @ModelAttribute final PlaceLocationAroundSearchRequest request
    ) {
        final List<PlaceDto> placeDtos = placeService.searchPlacesLocationAround(request.toSearchCondition());

        return CommonResponse.success(PlaceListResponse.of(placeDtos));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<PlaceListResponse>> searchTitle(
            @RequestParam(defaultValue = "0.0") final Double latitude,
            @RequestParam(defaultValue = "0.0") final Double longitude,
            @RequestParam(defaultValue = "240.0") final Double latitudeBound,
            @RequestParam(defaultValue = "180.0") final Double longitudeBound,
            @RequestParam final String name
    ) {
        final PlaceSearchCondition searchCondition = PlaceSearchCondition.builder()
                .latitude(latitude)
                .longitude(longitude)
                .latitudeBound(latitudeBound)
                .longitudeBound(longitudeBound)
                .name(name)
                .build();
        final List<PlaceDto> placeDtos = placeService.searchPlaces(searchCondition);

        return CommonResponse.success(PlaceListResponse.of(placeDtos));
    }

    @GetMapping("/tags")
    public ResponseEntity<CommonResponse<PlaceListResponse>> searchTaggedPlace(
            @RequestParam final List<Long> tagIds
    ) {
        final List<PlaceDto> placeDtos = placeService.searchPlacesByTags(tagIds);

        return CommonResponse.success(PlaceListResponse.of(placeDtos));
    }
}
