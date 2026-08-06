package com.example.rundrawbe.domain.home.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.enums.LevelType;
import com.example.rundrawbe.domain.home.dto.HomeResDTO;
import com.example.rundrawbe.domain.record.entity.CourseRecord;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant;

public class HomeConverter {

    public static HomeResDTO.MyCourseDTO toMyCourseDTO(CourseRecord record) {
        return HomeResDTO.MyCourseDTO.builder()
                .courseRecordId(record.getId())
                .courseDraftId(record.getCourseDraft().getCourseDraftId())
                .courseName(record.getCourseDraft().getName())
                .isCompleted(record.getIsCompleted())
                .recordedAt(record.getCreatedAt())
                .build();
    }

    public static HomeResDTO.MyRestaurantDTO toMyRestaurantDTO(Restaurant restaurant) {
        return HomeResDTO.MyRestaurantDTO.builder()
                .restaurantId(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .description(restaurant.getDescription())
                .url(restaurant.getUrl())
                .build();
    }

    public static HomeResDTO.GpsArtDTO toGpsArtDTO(Course course, long likeCount) {
        return HomeResDTO.GpsArtDTO.builder()
                .courseId(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .levelTag(toLevelTagLabel(course.getLevelTag().getLevelType()))
                .experienceCount(course.getExperienceCount())
                .likeCount(likeCount)
                .build();
    }

    private static String toLevelTagLabel(LevelType levelType) {
        return switch (levelType) {
            case BEGINNER -> "초급";
            case INTERMEDIATE -> "중급";
            case ADVANCED -> "고급";
        };
    }
}