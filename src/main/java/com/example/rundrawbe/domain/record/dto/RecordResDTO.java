package com.example.rundrawbe.domain.record.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RecordResDTO {  // server -> client
    // 기록 시작 응답
    public record StartResult(
            Long courseRecordId,
            LocalDateTime startAt
    ) {}

    // 종료 후 결과 화면용
    public record FinishResult(
            Long courseRecordId,
            Boolean isCompleted,
            Double distanceKm,
            Long durationSec,        // end_at - start_at (일시정지 시간 제외 여부는 Service에서 결정)
            List<PointDTO> points
    ) {}

    public record PointDTO(
            Integer sequence,
            Double latitude,
            Double longitude,
            LocalDateTime recordedAt
    ) {}
}
