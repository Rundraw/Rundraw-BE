package com.example.rundrawbe.domain.course.repository;
/* 상세조회 + 검색 + 위치기반 조회 */

import com.example.rundrawbe.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // SELECT * FROM course WHERE name LIKE '%keyword%'
    List<Course> findByNameContaining(String keyword);

    // 위치기반 조회: Course 자체엔 좌표가 없고 CourseDraft.points에 있으므로
    // 연관관계를 타고 들어가서 범위 검색
    @Query("""
        SELECT DISTINCT c FROM Course c
        JOIN c.courseDraft cd
        JOIN cd.points p
        WHERE p.latitude BETWEEN :minLat AND :maxLat
        AND p.longitude BETWEEN :minLng AND :maxLng
    """)
    List<Course> findByLocation(
            @Param("minLat") Double minLat, @Param("MaxLat") Double maxLat,
            @Param("minLng") Double minLng, @Param("MaxLng") Double maxLng
    );

}
