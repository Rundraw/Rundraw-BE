package com.example.rundrawbe.domain.mypage.repository;

import com.example.rundrawbe.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Course 엔티티(course 도메인 소유)는 건드리지 않고,
 * 마이페이지 도메인에서 "이 CourseDraft로 승격된 Course가 있는지" 조회만 하기 위한 전용 Repository.
 */
@Repository
public interface MypageCourseLookupRepository extends JpaRepository<Course, Long> {

    List<Course> findByCourseDraft_IdIn(List<Long> courseDraftIds);
}