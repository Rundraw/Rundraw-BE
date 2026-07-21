package com.example.rundrawbe.domain.course.repository;

import com.example.rundrawbe.domain.course.entity.LevelTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelTagRepository extends JpaRepository<LevelTag, Long> {
}
