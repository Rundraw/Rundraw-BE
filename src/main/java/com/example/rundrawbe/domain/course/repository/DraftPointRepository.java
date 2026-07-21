package com.example.rundrawbe.domain.course.repository;

import com.example.rundrawbe.domain.course.entity.DraftPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftPointRepository extends JpaRepository<DraftPoint, Long> {
}
