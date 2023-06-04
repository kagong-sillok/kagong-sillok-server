package org.prography.kagongsillok.place.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.place.application.PlaceService;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.ui.dto.PlaceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
