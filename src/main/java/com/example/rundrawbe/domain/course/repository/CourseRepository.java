package com.example.rundrawbe.domain.course.repository;
/* 상세조회 + 검색 + 위치기반 조회 */

import com.example.rundrawbe.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // SELECT * FROM course WHERE name LIKE '%keyword%'
    List<Course> findByNameContaining(String keyword);
}
