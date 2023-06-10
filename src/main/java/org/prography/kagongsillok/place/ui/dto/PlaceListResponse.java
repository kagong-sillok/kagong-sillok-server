package org.prography.kagongsillok.place.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.place.application.dto.PlaceDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceListResponse {

    private List<PlaceResponse> places;

    public static PlaceListResponse of(final List<PlaceDto> placeDtos) {
        return new PlaceListResponse(CustomListUtils.mapTo(placeDtos, PlaceResponse::from));
    }
}
