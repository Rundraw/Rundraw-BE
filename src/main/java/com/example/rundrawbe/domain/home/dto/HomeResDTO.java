package com.example.rundrawbe.domain.home.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class HomeResDTO {

    /**
     * GET /api/user/me/course - 내 경로 상위 N개
     * course_record + course_draft 조합
     * (CourseRecord에는 startAt/endAt이 없어서 BaseEntity의 createdAt 기준으로 최신순 표시)
     */
    @Getter
    @Builder
    public static class MyCourseDTO {
        private Long courseRecordId;
        private Long courseDraftId;
        private String courseName;   // course_draft.name
        private Boolean isCompleted; // 초록/빨강 점 표시용
        private LocalDateTime recordedAt; // BaseEntity.createdAt
    }

    /**
     * GET /api/user/me/restaurant - 저장한 맛집 상위 N개
     */
    @Getter
    @Builder
    public static class MyRestaurantDTO {
        private Long restaurantId;
        private String restaurantName;
        private Double latitude;
        private Double longitude;
        private String description;
        private String url;
    }

    /**
     * GET /api/gpsart - 저장된 인기 GPS 아트 상위 N개
     */
    @Getter
    @Builder
    public static class GpsArtDTO {
        private Long courseId;
        private String name;
        private String description;
        private String levelTag;
        private Integer experienceCount;
        private Long likeCount;
    }
}