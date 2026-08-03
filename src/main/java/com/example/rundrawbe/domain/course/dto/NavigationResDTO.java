package com.example.rundrawbe.domain.course.dto;

// - sequence 또는 좌표(lat, lng)
// - text (안내 문구, 예: "잠시 후 좌회전입니다")
// - triggerDistanceM (이 지점 몇 m 전에 안내할지, 기본값 30)

import java.util.List;

public class NavigationResDTO {

    public record Instruction(
            Integer sequence,
            Double latitude,
            Double longitude,
            String text,
            Double triggerDistanceM // (이 지점 몇 m 전에 안내할지, 기본값 30)
    ) {}

    public record NavigationList(List<Instruction> instructions) {}
}
