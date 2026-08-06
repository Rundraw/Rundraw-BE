package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseScrapRepository extends JpaRepository<CourseScrap, Long> {
    boolean existsByCourse_IdAndMember_Id(Long id, Long id1);
    List<CourseScrap> findByMember_Id(Long memberId);

    Optional<CourseScrap> findByCourseAndMember(Course course, Member member);

    boolean existsByCourseIdAndMemberId(Long courseId, Long memberId);

    Integer countByCourseId(Integer courseId);
}
