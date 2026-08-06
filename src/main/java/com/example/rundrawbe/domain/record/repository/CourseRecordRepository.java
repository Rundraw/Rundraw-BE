package com.example.rundrawbe.domain.record.repository;

import com.example.rundrawbe.domain.record.entity.CourseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRecordRepository extends JpaRepository<CourseRecord, Long> {
    List<CourseRecord> findByMember_Id(Long memberId);
    List<CourseRecord> findByMember_IdAndIsCompletedTrue(Long memberId);

    // 공유 토글 시 완주 여부 단건 확인용
    boolean existsByMember_IdAndCourseDraft_IdAndIsCompletedTrue(Long memberId, Long courseDraftId);
}