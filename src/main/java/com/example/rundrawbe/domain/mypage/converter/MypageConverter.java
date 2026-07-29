package com.example.rundrawbe.domain.mypage.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.mypage.dto.MypageResDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import com.example.rundrawbe.domain.record.entity.CourseRecord;

import java.util.List;
import java.util.stream.Collectors;

public class MypageConverter {

    public static MypageResDTO.CommentDTO toCommentDTO(Comment comment) {
        Course course = comment.getCourse();
        return MypageResDTO.CommentDTO.builder()
                .commentId(comment.getId())
                .courseId(course != null ? course.getId() : null)
                .courseName(course != null ? course.getName() : null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static MypageResDTO.CommentListDTO toCommentListDTO(List<Comment> comments) {
        return MypageResDTO.CommentListDTO.builder()
                .comments(comments.stream().map(MypageConverter::toCommentDTO).collect(Collectors.toList()))
                .build();
    }

    public static MypageResDTO.ScrapCourseDTO toScrapCourseDTO(CourseScrap scrap) {
        Course course = scrap.getCourse();
        return MypageResDTO.ScrapCourseDTO.builder()
                .courseId(course.getId())
                .courseName(course.getName())
                .experienceCount(course.getExperienceCount())
                .build();
    }

    public static MypageResDTO.ScrapCourseListDTO toScrapCourseListDTO(List<CourseScrap> scraps) {
        return MypageResDTO.ScrapCourseListDTO.builder()
                .scrapCourses(scraps.stream().map(MypageConverter::toScrapCourseDTO).collect(Collectors.toList()))
                .build();
    }

    public static MypageResDTO.CourseRecordDTO toCourseRecordDTO(CourseRecord record) {
        CourseDraft draft = record.getCourseDraft();
        return MypageResDTO.CourseRecordDTO.builder()
                .experienceRecordId(record.getId())
                .courseDraftId(draft != null ? draft.getCourseDraftId() : null)
                .courseName(draft != null ? draft.getName() : null)
                .isCompleted(record.getIsCompleted())
                .startAt(record.getStartAt())
                .endAt(record.getEndAt())
                .build();
    }

    public static MypageResDTO.CourseRecordListDTO toCourseRecordListDTO(List<CourseRecord> records) {
        return MypageResDTO.CourseRecordListDTO.builder()
                .courseRecords(records.stream().map(MypageConverter::toCourseRecordDTO).collect(Collectors.toList()))
                .build();
    }

    public static MypageResDTO.DraftCourseDTO toDraftCourseDTO(CourseDraft draft) {
        return MypageResDTO.DraftCourseDTO.builder()
                .draftCourseId(draft.getCourseDraftId())
                .name(draft.getName())
                .isSharing(draft.getIsSharing())
                .build();
    }

    public static MypageResDTO.DraftCourseListDTO toDraftCourseListDTO(List<CourseDraft> drafts) {
        return MypageResDTO.DraftCourseListDTO.builder()
                .draftCourses(drafts.stream().map(MypageConverter::toDraftCourseDTO).collect(Collectors.toList()))
                .build();
    }

    public static MypageResDTO.DraftPointDTO toDraftPointDTO(DraftPoint point) {
        return MypageResDTO.DraftPointDTO.builder()
                .sequence(point.getSequence())
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .build();
    }

    public static MypageResDTO.DraftCourseDetailDTO toDraftCourseDetailDTO(CourseDraft draft, List<DraftPoint> points) {
        return MypageResDTO.DraftCourseDetailDTO.builder()
                .draftCourseId(draft.getCourseDraftId())
                .name(draft.getName())
                .isSharing(draft.getIsSharing())
                .points(points.stream().map(MypageConverter::toDraftPointDTO).collect(Collectors.toList()))
                .build();
    }
}