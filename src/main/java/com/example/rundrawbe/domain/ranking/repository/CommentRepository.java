package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.ranking.entity.Comment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndMember_Id(Long commentId, Long id);

    boolean existsByIdAndCourse_Id(Long commentId, Long id);
    
    List<Comment> findByMember_IdOrderByCreatedAtDesc(Long memberId);

    Integer countByCourseId(Integer courseId);

    Slice<Comment> findByCourse_IdAndDeletedAtIsNullOrderByIdDesc(Long courseId, PageRequest pageRequest);

    Slice<Comment> findByCourse_IdAndDeletedAtIsNullAndIdLessThanOrderByIdDesc(Long courseId, long idCursor, PageRequest pageRequest);
}
