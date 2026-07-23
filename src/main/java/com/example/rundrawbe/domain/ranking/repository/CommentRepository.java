package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.ranking.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndMember_Id(Long commentId, Long id);

    boolean existsByIdAndCourse_Id(Long commentId, Long id);
}
