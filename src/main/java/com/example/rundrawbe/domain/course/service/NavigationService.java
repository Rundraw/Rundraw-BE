package com.example.rundrawbe.domain.course.service;

import com.example.rundrawbe.domain.course.dto.NavigationResDTO;
import com.example.rundrawbe.domain.course.entity.Course;
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

    // TODO 1: CourseRepository 또는 CourseDraftRepository 주입
    private final CourseRepository courseRepository;

    private static final double THRESHOLD_DEGREES = 30.0;
    private static final double DEFAULT_TRIGGER_DISTANCE_M = 30.0;


    // TODO 2: generateInstructions(courseId) 메서드
    //         - courseId로 Course(또는 CourseDraft) 조회
    //         - waypoints(DraftPoint 리스트) 가져오기
    //         - 좌표 3개씩 묶어서 반복(for문, i=1부터 size-2까지)
    //         - BearingCalculator로 이전 구간/다음 구간 방위각 계산
    //         - 두 방위각 차이가 임계값(30도) 넘으면 Instruction 생성
    //           (넘으면 방향 문구는 diff 양수/음수로 좌/우회전 구분)
    //         - 만든 Instruction 리스트를 NavigationResDTO로 변환해 반환
    public NavigationResDTO.NavigationList generateInstructions(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다: " + courseId));

        List<DraftPoint> points = course.getCourseDraft().getPoints();

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
