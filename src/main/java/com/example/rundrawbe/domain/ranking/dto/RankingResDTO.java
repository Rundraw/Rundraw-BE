package com.example.rundrawbe.domain.ranking.dto;

import com.example.rundrawbe.domain.course.enums.LevelType;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

public class RankingResDTO {

    // 페이지네이션 틀(커서 기반)
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}

    // 댓글 조회
    @Builder
    public record GetComment(
            Long id,
            String memberName,
            String content,
            LocalDateTime createdAt,
            Boolean isMine
    ){}

    // 코스 조회
    @Builder
    public record GetRanking(
            Long id,
            String name,
            Integer experienceCount
    ){}

    // gps art 조회
    @Builder
    public record GetGpsArt (
        Long id,
        String name,
        Integer likeCount,
        List<Point> points
    ){}

    @Builder
    public record Point(
        Double latitude,
        Double longitude
    ){}

    // 코스 상세 조회
    @Builder
    public record GetCourseDetail(
            Long courseId,
            String name,
            String content,
            LevelType levelType,
            String user,
            Boolean isLike,
            Boolean isBookmark,
            Integer commentCount,
            Integer likeCount,
            Integer bookmarkCount,
            List<Point> points
    ){}
}
