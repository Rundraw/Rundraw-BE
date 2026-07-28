package com.example.rundrawbe.domain.course.entity;

import com.example.rundrawbe.global.entity.PointBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class DraftPoint extends PointBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_draft_id")
    @Setter
    private CourseDraft courseDraft;
}
