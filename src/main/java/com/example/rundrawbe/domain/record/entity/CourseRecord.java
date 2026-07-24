package com.example.rundrawbe.domain.record.entity;

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_draft_id")
    private CourseDraft courseDraft;

    @Builder.Default
    private Boolean isCompleted = false;
}
