package com.example.rundrawbe.domain.course.repository;
/* 그린 코스 저장 */

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDraftRepository extends JpaRepository<CourseDraft, Long> {
}
