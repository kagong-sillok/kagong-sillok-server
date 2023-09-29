package org.prography.kagongsillok.place.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceSurfaceDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceSearchResultResponse {

    private List<PlaceSurfaceResponse> places;

    public static PlaceSearchResultResponse of (final List<PlaceSurfaceDto> placeDtos) {
        return new PlaceSearchResultResponse(CustomListUtils.mapTo(placeDtos, PlaceSurfaceResponse::from));
    }
}
