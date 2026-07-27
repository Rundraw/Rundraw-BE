package com.example.rundrawbe.domain.course.entity;

import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_draft_id")
    private CourseDraft courseDraft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_tag_id")
    private LevelTag levelTag;

    private String name;

    private Integer experienceCount;

    private String description;
}
