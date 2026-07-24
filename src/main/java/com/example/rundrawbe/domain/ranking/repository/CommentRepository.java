package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.ranking.entity.Comment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndMember_Id(Long commentId, Long id);

    boolean existsByIdAndCourse_Id(Long commentId, Long id);

    Slice<Comment> findByCourse_IdAndIdLessThanOrderByIdDesc(Long courseId, Long idCursor, Pageable pageable);

    Slice<Comment> findByCourse_IdOrderByIdDesc(Long courseId, Pageable pageable);
}
