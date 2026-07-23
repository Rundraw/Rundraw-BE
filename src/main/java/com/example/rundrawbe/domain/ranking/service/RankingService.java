package com.example.rundrawbe.domain.ranking.service;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.component.CourseFinder;
import com.example.rundrawbe.domain.ranking.converter.RankingConverter;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.dto.RankingResDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.exception.RankingException;
import com.example.rundrawbe.domain.ranking.exception.code.RankingErrorCode;
import com.example.rundrawbe.domain.ranking.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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

    public RankingResDTO.Pagination<RankingResDTO.GetComment> getComment(
            Long courseId, Integer pageSize, String cursor, String query
    ) {
        // 페이지 정보 생성
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        long idCursor;
        Slice<Comment> commentList;

        // 첫 페이지 조회
        if (cursor == null || "-1".equals(cursor)) {
            commentList = commentRepository.findByCourse_IdOrderByIdDesc(courseId, pageRequest);

        } else {
            // 커서 분리
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()) {
                case "id":
                    idCursor = Long.parseLong(cursorSplit[1]);
                    commentList = commentRepository.findByCourse_IdAndIdLessThanOrderByIdDesc(courseId, idCursor, pageRequest);
                    break;
                default:
                    throw new RankingException(RankingErrorCode.QUERY_NOT_VALID);
            }
        }
        // 다음 커서 계산
        String nextCursor = null;
        if (!commentList.isEmpty() && commentList.hasNext()) {
            nextCursor = "id:" + commentList.getContent().getLast().getId();
        }
        // 응답 DTO 반환
        return RankingConverter.toPagination(
                commentList.map(RankingConverter::toGetComment).toList(),
                commentList.hasNext(),
                nextCursor,
                commentList.getNumberOfElements()
        );
    }
}
