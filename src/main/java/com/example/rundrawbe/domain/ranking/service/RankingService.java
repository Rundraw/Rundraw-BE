package com.example.rundrawbe.domain.ranking.service;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.converter.RankingConverter;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.exception.RankingException;
import com.example.rundrawbe.domain.ranking.exception.code.RankingErrorCode;
import com.example.rundrawbe.domain.ranking.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingService {

    private final CourseRepository courseRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    public Long createComment(Long courseId, RankingReqDTO.CreateComment dto, Member member) {
        // 코스 조회
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RankingException(RankingErrorCode.COURSE_NOT_FOUND));
        // 댓글 생성
        Comment comment = RankingConverter.toCreateComment(dto, course, member);
        commentRepository.save(comment);
        return comment.getId();
    }
}
