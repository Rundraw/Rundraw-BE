package com.example.rundrawbe.domain.ranking.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.dto.RankingResDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.entity.CourseLike;

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

    public static RankingResDTO.GetComment toGetComment(Comment comment) {
        return RankingResDTO.GetComment.builder()
                .id(comment.getId())
                .memberName(comment.getMember().getName())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .build();
    }

    public static CourseLike toCreateLike(Course course, Member member) {
        return CourseLike.builder()
                .member(member)
                .course(course)
                .build();
    }
}
