package com.example.rundrawbe.domain.draft.repository;

import com.example.rundrawbe.domain.draft.entity.CourseDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftRepository extends JpaRepository<CourseDraft, Long> {
}
