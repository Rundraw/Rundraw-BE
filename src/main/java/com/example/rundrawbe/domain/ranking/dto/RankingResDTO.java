package com.example.rundrawbe.domain.ranking.dto;

import com.example.rundrawbe.domain.course.entity.Course;
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
            LocalDateTime createdAt
    ){}

    // 코스 조회
    @Builder
    public record GetRanking(
            Long id,
            String name,
            Integer experienceCount
    ){}
}
