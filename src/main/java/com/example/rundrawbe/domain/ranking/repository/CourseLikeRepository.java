package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.ranking.entity.CourseLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
}
