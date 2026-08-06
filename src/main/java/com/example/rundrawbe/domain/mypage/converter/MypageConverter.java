package com.example.rundrawbe.domain.mypage.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.mypage.dto.MypageResDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import com.example.rundrawbe.domain.record.entity.CourseRecord;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
                .courseDraftId(draft != null ? draft.getId() : null)
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

    // 기존: isCompleted 정보 없이 변환 (다른 곳에서 계속 쓰고 있으면 유지)
    public static MypageResDTO.DraftCourseDTO toDraftCourseDTO(CourseDraft draft) {
        return MypageResDTO.DraftCourseDTO.builder()
                .draftCourseId(draft.getId())
                .name(draft.getName())
                .isSharing(draft.getIsSharing())
                .build();
    }

    // ★ 추가: isCompleted, courseId(승격 여부) 포함 변환
    public static MypageResDTO.DraftCourseDTO toDraftCourseDTO(CourseDraft draft, boolean isCompleted, Long courseId) {
        return MypageResDTO.DraftCourseDTO.builder()
                .draftCourseId(draft.getId())
                .name(draft.getName())
                .isSharing(draft.getIsSharing())
                .isCompleted(isCompleted)
                .courseId(courseId)
                .build();
    }

    public static MypageResDTO.DraftCourseListDTO toDraftCourseListDTO(List<CourseDraft> drafts) {
        return MypageResDTO.DraftCourseListDTO.builder()
                .draftCourses(drafts.stream().map(MypageConverter::toDraftCourseDTO).collect(Collectors.toList()))
                .build();
    }

    // ★ 추가: 완주한 draftId 집합 + (draftId -> courseId) 매핑을 받아서 채워 변환
    public static MypageResDTO.DraftCourseListDTO toDraftCourseListDTO(
            List<CourseDraft> drafts,
            Set<Long> completedDraftIds,
            Map<Long, Long> draftIdToCourseIdMap
    ) {
        return MypageResDTO.DraftCourseListDTO.builder()
                .draftCourses(drafts.stream()
                        .map(draft -> toDraftCourseDTO(
                                draft,
                                completedDraftIds.contains(draft.getId()),
                                draftIdToCourseIdMap.get(draft.getId())
                        ))
                        .collect(Collectors.toList()))
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
                .draftCourseId(draft.getId())
                .name(draft.getName())
                .isSharing(draft.getIsSharing())
                .points(points.stream().map(MypageConverter::toDraftPointDTO).collect(Collectors.toList()))
                .build();
    }
}