package com.example.rundrawbe.domain.course.service;

import com.example.rundrawbe.domain.course.dto.NavigationResDTO;
import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.course.repository.CourseDraftRepository;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.course.util.BearingCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NavigationService {

    private final CourseRepository courseRepository;
    private final CourseDraftRepository courseDraftRepository;

    private static final double THRESHOLD_DEGREES = 30.0;
    private static final double DEFAULT_TRIGGER_DISTANCE_M = 30.0;

    public NavigationResDTO.NavigationList generateInstructions(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다: " + courseId));

        return buildInstructions(course.getCourseDraft().getPoints());
    }

    public NavigationResDTO.NavigationList generateInstructionsFromDraft(Long courseDraftId) {
        CourseDraft draft = courseDraftRepository.findById(courseDraftId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다: " + courseDraftId));

        return buildInstructions(draft.getPoints());
    }

    // 공통 로직 — waypoints를 받아 회전 안내 리스트 생성
    private NavigationResDTO.NavigationList buildInstructions(List<DraftPoint> points) {
        List<NavigationResDTO.Instruction> instructions = new ArrayList<>();

        for (int i = 1; i < points.size() - 1; i++) {
            DraftPoint prev = points.get(i - 1);
            DraftPoint current = points.get(i);
            DraftPoint next = points.get(i + 1);

            double bearing1 = BearingCalculator.calculateBearing(
                    prev.getLatitude(), prev.getLongitude(),
                    current.getLatitude(), current.getLongitude());

            double bearing2 = BearingCalculator.calculateBearing(
                    current.getLatitude(), current.getLongitude(),
                    next.getLatitude(), next.getLongitude());

            double diff = BearingCalculator.bearingDiff(bearing1, bearing2);

            if (Math.abs(diff) < THRESHOLD_DEGREES) {
                continue; // 직진 — 안내 생략
            }

            String text = diff > 0 ? "잠시 후 우회전입니다" : "잠시 후 좌회전입니다";

            instructions.add(new NavigationResDTO.Instruction(
                    current.getSequence(),
                    current.getLatitude(),
                    current.getLongitude(),
                    text,
                    DEFAULT_TRIGGER_DISTANCE_M
            ));
        }

        return new NavigationResDTO.NavigationList(instructions);
    }

}
