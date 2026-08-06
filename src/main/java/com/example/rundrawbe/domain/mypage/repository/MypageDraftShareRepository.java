package com.example.rundrawbe.domain.mypage.repository;

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * CourseDraft 엔티티(course 도메인 소유)는 건드리지 않고,
 * 마이페이지 도메인에서 isSharing 필드만 업데이트하기 위한 전용 Repository.
 */
@Repository
public interface MypageDraftShareRepository extends JpaRepository<CourseDraft, Long> {

    @Modifying
    @Query("UPDATE CourseDraft d SET d.isSharing = :isSharing WHERE d.id = :draftCourseId AND d.member.id = :memberId")
    int updateSharingStatus(
            @Param("draftCourseId") Long draftCourseId,
            @Param("memberId") Long memberId,
            @Param("isSharing") boolean isSharing
    );
}