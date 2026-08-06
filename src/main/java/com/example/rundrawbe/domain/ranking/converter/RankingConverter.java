package com.example.rundrawbe.domain.ranking.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.dto.RankingResDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.entity.CourseLike;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import java.util.List;

public class RankingConverter {

    public static Comment toCreateComment(
            RankingReqDTO.CreateComment dto,
            Course course,
            Member member
    ){
        return Comment.builder()
                .course(course)
                .member(member)
                .content(dto.comment())
                .build();
    }

    // 페이지네이션 틀 생성(커서 기반)
    public static <T> RankingResDTO.Pagination<T> toPagination(
            List<T> data,
            boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){
        return RankingResDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }

    public static RankingResDTO.GetComment toGetComment(Comment comment, Member member) {
        return RankingResDTO.GetComment.builder()
                .id(comment.getId())
                .memberName(comment.getMember().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .isMine(comment.getMember().getId().equals(member.getId()))
                .build();
    }

    public static CourseLike toCreateLike(Course course, Member member) {
        return CourseLike.builder()
                .member(member)
                .course(course)
                .build();
    }

    public static CourseScrap toCreateScrap(Course course, Member member) {
        return CourseScrap.builder()
                .member(member)
                .course(course)
                .build();
    }

    public static RankingResDTO.GetRanking toGetRanking(Course course){
        return RankingResDTO.GetRanking.builder()
                .id(course.getId())
                .name(course.getName())
                .experienceCount(course.getExperienceCount())
                .build();
    }

    public static RankingResDTO.GetGpsArt toGetGpsArt(
            Course course,
            Integer likeCount
    ) {
        List<RankingResDTO.Point> points = course.getCourseDraft()
                .getPoints()
                .stream()
                .map(point -> RankingResDTO.Point.builder()
                        .latitude(point.getLatitude())
                        .longitude(point.getLongitude())
                        .build()
                )
                .toList();
        return RankingResDTO.GetGpsArt.builder()
                .id(course.getId())
                .name(course.getName())
                .likeCount(likeCount)
                .points(points)
                .build();
    }

    public static RankingResDTO.GetCourseDetail toGetCourseDetail(
            Course course,
            boolean isLike,
            boolean isBookmark,
            List<RankingResDTO.Point> points,
            Integer commentCount,
            Integer likeCount,
            Integer bookmarkCount,
            Long courseDraftId
    ){
        return RankingResDTO.GetCourseDetail.builder()
                .courseId(course.getId())
                .name(course.getName())
                .content(course.getDescription())
                .levelType(course.getLevelTag().getLevelType())
                .user(course.getCourseDraft().getMember().getName())
                .isLike(isLike)
                .isBookmark(isBookmark)
                .points(points)
                .commentCount(commentCount)
                .bookmarkCount(bookmarkCount)
                .likeCount(likeCount)
                .coursedraftId(courseDraftId)
                .build();
    }
}
