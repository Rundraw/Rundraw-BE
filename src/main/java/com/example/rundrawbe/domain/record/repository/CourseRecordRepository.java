package com.example.rundrawbe.domain.record.repository;

import com.example.rundrawbe.domain.record.entity.CourseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRecordRepository extends JpaRepository<CourseRecord, Long> {
}
