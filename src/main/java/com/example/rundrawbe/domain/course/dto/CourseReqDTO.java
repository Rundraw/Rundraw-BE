package com.example.rundrawbe.domain.course.dto;

import java.util.List;

public class CourseReqDTO { // client -> server
    // 그린 코스 저장 요청
    public record CreateDraft(
            String name,
            Long memberId,
            List<PointDTO> points
    ) {}

    // 좌표 하나
    public record PointDTO(
            Integer sequence,
            Double latitude,
            Double longitude
    ) {}

    // 코스 수정 요청
    public record UpdateCourse(
            String name,
            String description,
            String levelTagName // 예: "BEGINNER", "INTERMEDIATE", "ADVANCED"
    ) {}
}
