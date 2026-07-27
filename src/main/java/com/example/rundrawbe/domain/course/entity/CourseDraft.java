package com.example.rundrawbe.domain.course.entity;

import com.example.rundrawbe.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourseDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseDraftId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // member 객체

    @Column(nullable = false)
    private Boolean isSharing = false;

    @OneToMany(mappedBy = "courseDraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DraftPoint> points = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    // 연관관계 편의 메서드 — points를 추가할 때 양쪽 다 세팅해주는 헬퍼
    public void addPoint(DraftPoint point){
        points.add(point);
        point.setCourseDraft(this);
    }
}
