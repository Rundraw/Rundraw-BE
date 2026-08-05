package com.example.rundrawbe.domain.course.service;
/* 그린 코스 저장 */

import com.example.rundrawbe.domain.course.dto.CourseReqDTO;
import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.course.repository.CourseDraftRepository;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseDraftService {

    private final CourseDraftRepository courseDraftRepository;
    private final MemberRepository memberRepository;

    public CourseResDTO.DraftDetail saveDraft(CourseReqDTO.CreateDraft request) {
        // memberId로 실제 Member 엔티티 조회
        Member memberEntity = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 1. 빈 CourseDraft 객체 생성 (request dto 를 받아 저장)
        CourseDraft draft = CourseDraft.builder()
                .name(request.name())
                .member(memberEntity)
                .isSharing(false)
                .build();
        // (name, memberId는 Entity에 생성자나 setter가 없어서 다음 단계에서 손볼 거예요 — 일단 흐름만 보세요)
        // (name, member 채우는 부분은 Builder 방식으로 다음에 정리)

        // 2. 요청으로 받은 좌표들을 DraftPoint로 변환해서 draft에 연결
        for (CourseReqDTO.PointDTO p : request.points()) {
            DraftPoint point = new DraftPoint();
            point.setSequence(p.sequence());
            point.setLatitude(p.latitude());
            point.setLongitude(p.longitude());
            draft.addPoint(point); // 연관관계 편의 메서드 (@OneToMany/@ManyToOne 양방향 관계)
        }

        // 3. 저장 (JpaRepository가 기본 제공하는 save() 사용)
        CourseDraft saved = courseDraftRepository.save(draft);

        // 4. 저장된 결과를 응답 DTO로 변환해서 반환
        List<CourseResDTO.PointDTO> pointDTOs = saved.getPoints().stream()
                .map(p -> new CourseResDTO.PointDTO(p.getSequence(), p.getLatitude(), p.getLongitude()))
                .collect(Collectors.toList());

        return new CourseResDTO.DraftDetail(
                saved.getCourseDraftId(),
                saved.getName(),
                saved.getIsSharing(),
                pointDTOs,
                saved.getCreatedAt()
        );
    }

    public CourseResDTO.DraftDetail getDraftDetail(Long courseDraftId) {
        CourseDraft draft = courseDraftRepository.findById(courseDraftId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다: " + courseDraftId));

        List<CourseResDTO.PointDTO> pointDTOs = draft.getPoints().stream()
                .map(p -> new CourseResDTO.PointDTO(p.getSequence(), p.getLatitude(), p.getLongitude()))
                .collect(Collectors.toList());

        Boolean isSharing = draft.getIsSharing() != null ? draft.getIsSharing() : false;

        return new CourseResDTO.DraftDetail(
                draft.getCourseDraftId(),
                draft.getName(),
                isSharing,
                pointDTOs,
                draft.getCreatedAt()
        );
    }
}
