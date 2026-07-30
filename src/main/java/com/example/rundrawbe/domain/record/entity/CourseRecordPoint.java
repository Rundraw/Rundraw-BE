package com.example.rundrawbe.domain.record.entity;

import com.example.rundrawbe.global.entity.PointBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseRecordPoint extends PointBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_record_id")
    private CourseRecord courseRecord;

    private LocalDateTime recordedAt;
}
