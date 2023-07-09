package org.prography.kagongsillok.record.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.record.application.RecordService;
import org.prography.kagongsillok.record.application.dto.RecordDto;
import org.prography.kagongsillok.record.ui.dto.RecordCreateRequest;
import org.prography.kagongsillok.record.ui.dto.RecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/records")
public class RecordV1Controller {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<CommonResponse<RecordResponse>> createRecord(
            @RequestBody final RecordCreateRequest recordCreateRequest
    ) {
        final RecordDto createRecord = recordService.createRecord(recordCreateRequest.toCommand());

        return CommonResponse.success(RecordResponse.from(createRecord));
    }

}
