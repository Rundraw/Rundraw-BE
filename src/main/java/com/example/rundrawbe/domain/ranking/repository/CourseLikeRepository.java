package com.example.rundrawbe.domain.ranking.repository;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.entity.CourseLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
    boolean existsByCourse_IdAndMember_Id(Long id, Long id1);

    Optional<CourseLike> findByCourseAndMember(Course course, Member member);

    Integer countByCourse_Id(Long id);

    boolean existsByCourseIdAndMemberId(Long courseId, Long memberId);

    Integer countByCourseId(Integer courseId);
}
