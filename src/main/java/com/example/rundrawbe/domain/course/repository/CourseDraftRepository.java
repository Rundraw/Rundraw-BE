package com.example.rundrawbe.domain.course.repository;
/* 그린 코스 저장 */

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseDraftRepository extends JpaRepository<CourseDraft, Long> {
    List<CourseDraft> findByMember_Id(Long memberId);
}
