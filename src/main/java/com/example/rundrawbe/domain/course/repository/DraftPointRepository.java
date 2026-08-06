package com.example.rundrawbe.domain.course.repository;

import com.example.rundrawbe.domain.course.entity.DraftPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DraftPointRepository extends JpaRepository<DraftPoint, Long> {
    List<DraftPoint> findByCourseDraft_IdOrderBySequenceAsc(Long courseDraftId);
}
