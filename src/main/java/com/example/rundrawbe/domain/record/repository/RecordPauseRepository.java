package com.example.rundrawbe.domain.record.repository;

import com.example.rundrawbe.domain.record.entity.RecordPause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPauseRepository extends JpaRepository<RecordPause, Long> {
}
