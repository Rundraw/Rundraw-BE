package com.example.rundrawbe.domain.record.entity;

import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RecordPause extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_record_id")
    private CourseRecord courseRecord;

    private Double latitude;

    private Double longitude;
}
