package org.prography.kagongsillok.record.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.common.web.dto.CommonResponse;
import org.prography.kagongsillok.place.application.dto.PlaceDto;
import org.prography.kagongsillok.place.ui.dto.PlaceListResponse;
import org.prography.kagongsillok.record.application.StudyRecordService;
import org.prography.kagongsillok.record.application.dto.StudyRecordDto;
import org.prography.kagongsillok.record.ui.dto.StudyRecordCreateRequest;
import org.prography.kagongsillok.record.ui.dto.StudyRecordListResponse;
import org.prography.kagongsillok.record.ui.dto.StudyRecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-records")
public class StudyRecordV1Controller {

    private final StudyRecordService studyRecordService;

    @PostMapping
    public ResponseEntity<CommonResponse<StudyRecordResponse>> createRecord(
            @RequestBody final StudyRecordCreateRequest studyRecordCreateRequest
    ) {
        final StudyRecordDto createRecord = studyRecordService.createStudyRecord(studyRecordCreateRequest.toCommand());

        return CommonResponse.success(StudyRecordResponse.from(createRecord));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommonResponse<StudyRecordListResponse>> getMemberRecords(
            @PathVariable("memberId") Long memberId
    ) {
        final List<StudyRecordDto> studyRecordDtos = studyRecordService.getMemberStudyRecords(memberId);

        return CommonResponse.success(StudyRecordListResponse.of(studyRecordDtos));
    }

    @GetMapping("/timelines/{memberId}")
    public ResponseEntity<CommonResponse<StudyRecordListResponse>> getMemberRecordsByYearMonth(
            @PathVariable("memberId") Long memberId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        final List<StudyRecordDto> studyRecordDtos = studyRecordService.getMemberStudyRecordsByYearMonth(memberId, year, month);

        return CommonResponse.success(StudyRecordListResponse.of(studyRecordDtos));
    }

    @GetMapping("/places/{memberId}")
    public ResponseEntity<CommonResponse<PlaceListResponse>> getStudyPlaces(
            @PathVariable("memberId") Long memberId
    ) {
        final List<PlaceDto> placeDtos = studyRecordService.getMemberStudyPlaces(memberId);

        return CommonResponse.success(PlaceListResponse.of(placeDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyRecord(@PathVariable("id") final Long id) {
        studyRecordService.deleteStudyRecord(id);
        return ResponseEntity.ok().build();
    }
}
