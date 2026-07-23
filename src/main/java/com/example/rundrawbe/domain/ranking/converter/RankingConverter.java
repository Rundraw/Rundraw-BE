package com.example.rundrawbe.domain.ranking.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;

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
}
