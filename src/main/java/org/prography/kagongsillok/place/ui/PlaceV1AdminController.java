package org.prography.kagongsillok.place.ui;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.place.application.PlaceService;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.ui.dto.PlaceCreateRequest;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;
import org.prography.kagongsillok.place.ui.dto.PlaceUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/places")
public class PlaceV1AdminController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<CommonResponse<PlaceResponse>> createPlace(
            @RequestBody final PlaceCreateRequest placeCreateRequest
    ) {
        final PlaceDto createdPlace = placeService.createPlace(placeCreateRequest.toCommand());
        return CommonResponse.success(PlaceResponse.from(createdPlace));
    }

    @GetMapping("/{id}")
    @Operation(summary = "장소 단건 조회 API", description = "장소 id로 쿠폰을 조회합니다.")
    public ResponseEntity<CommonResponse<PlaceResponse>> getPlace(@PathVariable("id") final Long id) {
        final PlaceDto placeDto = placeService.getPlace(id);
        return CommonResponse.success(PlaceResponse.from(placeDto));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<PlaceResponse>> updatePlace(
            @RequestBody final PlaceUpdateRequest placeUpdateRequest
    ) {
        final PlaceDto createdPlace = placeService.updatePlace(placeUpdateRequest.getId(), placeUpdateRequest.toCommand());
        return CommonResponse.success(PlaceResponse.from(createdPlace));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") final Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.ok().build();
    }
}
