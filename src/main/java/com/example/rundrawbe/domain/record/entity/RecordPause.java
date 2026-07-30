package com.example.rundrawbe.domain.record.entity;

import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RecordPause extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_record_id")
    private CourseRecord courseRecord;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Setter
    private LocalDateTime endAt; // 재개 시 채워짐, 그 전까지 null

    private Double latitude;
    private Double longitude;
}
