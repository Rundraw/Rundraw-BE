package com.example.rundrawbe.domain.record.entity;

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Double distanceKm;

    @OneToMany(mappedBy = "courseRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CourseRecordPoint> points = new ArrayList<>();

    @OneToMany(mappedBy = "courseRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RecordPause> pauses = new ArrayList<>();

    public void addPoint(CourseRecordPoint point) {
        points.add(point);
        point.setCourseRecord(this);
    }
}
