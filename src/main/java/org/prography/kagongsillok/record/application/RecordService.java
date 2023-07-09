package org.prography.kagongsillok.record.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.record.domain.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;



}
