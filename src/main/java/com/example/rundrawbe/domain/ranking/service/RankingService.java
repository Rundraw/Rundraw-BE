package com.example.rundrawbe.domain.ranking.service;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.component.CourseFinder;
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
    private final CourseFinder courseFinder;

    // 댓글 작성
    public Long createComment(Long courseId, RankingReqDTO.CreateComment dto, Member member) {
        // 코스 조회
        Course course = courseFinder.findById(courseId);
        // 댓글 생성
        Comment comment = RankingConverter.toCreateComment(dto, course, member);
        commentRepository.save(comment);
        return comment.getId();
    }

    public Object updateComment(Long courseId, Long commentId, RankingReqDTO.UpdateComment dto, Member member) {
        // 코스 조회
        Course course = courseFinder.findById(courseId);
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RankingException(RankingErrorCode.COMMENT_NOT_FOUND));
        // 수정 권한 검토
        if(!commentRepository.existsByIdAndMember_Id(commentId, member.getId())) {
            throw new RankingException(RankingErrorCode.COMMENT_ACCESS_DENIED);
        }
        // 댓글 수정
        comment.updateComment(dto.comment());
        return null;
    }

    // 댓글 삭제
    public Object deleteComment(Long courseId, Long commentId, Member member) {
        // 코스 조회
        Course course = courseFinder.findById(courseId);
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RankingException(RankingErrorCode.COMMENT_NOT_FOUND));
        if(!commentRepository.existsByIdAndCourse_Id(commentId,course.getId())){
            throw new RankingException(RankingErrorCode.COMMENT_NOT_FOUND);
        }
        if(comment.getDeletedAt()!=null){
            throw new RankingException(RankingErrorCode.COMMENT_ALREADY_DELETED);
        }
        // 수정 권한 검토
        if(!commentRepository.existsByIdAndMember_Id(commentId, member.getId())) {
            throw new RankingException(RankingErrorCode.COMMENT_ACCESS_DENIED);
        }
        // 댓글 삭제
        comment.deleteComment();
        return null;
    }
}
