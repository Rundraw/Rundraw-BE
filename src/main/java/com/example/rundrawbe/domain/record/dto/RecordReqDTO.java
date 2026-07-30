package com.example.rundrawbe.domain.record.dto;

public class RecordReqDTO { // client -> server
    public record Start(Long courseDraftId) {}

    public record SavePoint(
            Long recordId,
            Integer sequence,
            Double latitude,
            Double longitude
    ) {}

    public record Pause(
            Long recordId,
            Double latitude,
            Double longitude
    ) {}
}
