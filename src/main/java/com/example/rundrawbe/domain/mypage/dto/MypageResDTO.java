package com.example.rundrawbe.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class MypageResDTO {

    @Getter @Builder
    public static class CommentDTO {
        private Long commentId;
        private Long courseId;
        private String courseName;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter @Builder
    public static class CommentListDTO {
        private List<CommentDTO> comments;
    }

    @Getter @Builder
    public static class ScrapCourseDTO {
        private Long courseId;
        private String courseName;
        private Integer experienceCount;
    }

    @Getter @Builder
    public static class ScrapCourseListDTO {
        private List<ScrapCourseDTO> scrapCourses;
    }

    @Getter @Builder
    public static class CourseRecordDTO {
        private Long experienceRecordId;
        private Long courseDraftId;
        private String courseName;
        private Boolean isCompleted;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
    }

    @Getter @Builder
    public static class CourseRecordListDTO {
        private List<CourseRecordDTO> courseRecords;
    }

    @Getter @Builder
    public static class DraftCourseDTO {
        private Long draftCourseId;
        private String name;
        private Boolean isSharing;
    }

    @Getter @Builder
    public static class DraftCourseListDTO {
        private List<DraftCourseDTO> draftCourses;
    }

    @Getter @Builder
    public static class DraftPointDTO {
        private Integer sequence;
        private Double latitude;
        private Double longitude;
    }

    @Getter @Builder
    public static class DraftCourseDetailDTO {
        private Long draftCourseId;
        private String name;
        private Boolean isSharing;
        private List<DraftPointDTO> points;
    }
}