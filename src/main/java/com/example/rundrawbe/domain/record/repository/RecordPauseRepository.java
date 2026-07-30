package com.example.rundrawbe.domain.record.repository;

import com.example.rundrawbe.domain.record.entity.RecordPause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordPauseRepository extends JpaRepository<RecordPause, Long> {
    // 재개(resume) API에서 "현재 진행 중인 일시정지"를 찾을 때 사용
    Optional<RecordPause> findFirstByCourseRecordIdAndEndAtIsNull(Long courseRecordId);
}