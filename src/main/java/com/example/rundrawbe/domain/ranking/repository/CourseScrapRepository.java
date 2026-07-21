package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScrapRepository extends JpaRepository<CourseScrap, Long> {
}
