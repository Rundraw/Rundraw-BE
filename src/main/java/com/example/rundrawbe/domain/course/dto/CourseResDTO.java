package com.example.rundrawbe.domain.course.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CourseResDTO {  // server -> client
    // 그린 코스 저장/조회 응답
    public record DraftDetail(
            Long courseDraftId,
            String name,
            boolean isSharing,
            List<PointDTO> points,
            LocalDateTime createAt
    ) {}

    // 코스 목록 조회용 (위치기반, 검색) — 좌표 배열 없이 요약 정보만
    public record Summary(
            Long courseId,
            String name,
            Integer experienceCount,
            String description
    ) {}

    // 코스 상세 조회 응답
    public record Detail(
            Long courseId,
            String name,
            Integer experienceCount,
            String description,
            String levelTagName
    ) {}

    public record PointDTO(
            Integer sequence,
            Double latitude,
            Double longitude
    ) {}
}
