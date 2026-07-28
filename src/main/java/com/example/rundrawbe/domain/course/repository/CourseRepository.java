package com.example.rundrawbe.domain.course.repository;
/* 상세조회 + 검색 + 위치기반 조회 */

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.enums.LevelType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // 인기순은 Spring이 자동 처리, 거리순은 좌표까지 같이 가져오기

    // 인기순 검색: Sort 파라미터를 받으면 Spring이 자동으로 ORDER BY 처리
    // SELECT * FROM course WHERE name LIKE '%keyword%'
    List<Course> findByNameContaining(String keyword, org.springframework.data.domain.Sort sort);

    // 거리순 검색용 (코스 대표 좌표까지 필요해서 courseDraft까지 조인)
    @Query("""
        SELECT c FROM Course c
        JOIN c.courseDraft cd
        WHERE c.name LIKE %:keyword%
    """)
    List<Course> findByNameContainingWithDraft(@Param("keyword") String keyword);


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
            @Param("minLat") Double minLat, @Param("maxLat") Double maxLat,
            @Param("minLng") Double minLng, @Param("maxLng") Double maxLng
    );

    Slice<Course> findAllByOrderByIdDesc(PageRequest pageRequest);

    Slice<Course> findByIdLessThanOrderByIdDesc(long idCursor, PageRequest pageRequest);

    Slice<Course> findAllByOrderByExperienceCountDescIdDesc(PageRequest pageRequest);

    Slice<Course> findByIdLessThanOrderByExperienceCountDescIdDesc(long idCursor, PageRequest pageRequest);

    Slice<Course> findByLevelTag_LevelTypeOrderByIdDesc(LevelType levelType, PageRequest pageRequest);

    Slice<Course> findByLevelTag_LevelTypeAndIdLessThanOrderByIdDesc(LevelType levelType, Long idCursor, PageRequest pageRequest);
}
