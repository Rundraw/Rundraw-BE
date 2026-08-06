package com.example.rundrawbe.domain.course.service;

import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseResDTO.Detail getDetail(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다: " + courseId));

        return new CourseResDTO.Detail(
                course.getId(),
                course.getName(),
                course.getExperienceCount(),
                course.getDescription(),
                course.getLevelTag() != null ? course.getLevelTag().getLevelType().name() : null
        );
    }

    public List<CourseResDTO.Summary> search(String keyword, String sort, Double lat, Double lng) {
        if ("distance".equals(sort)) {
            return  searchByDistance(keyword, lat, lng);
        }
        // 기본값은 popular
        return searchByPopularity(keyword);
    }

    private List<CourseResDTO.Summary> searchByPopularity(String keyword) {
        Sort sort = Sort.by(Sort.Direction.DESC, "experienceCount");
        return  courseRepository.findByNameContaining(keyword, sort).stream()
                .map(c -> new CourseResDTO.Summary(
                        c.getId(), c.getName(), c.getExperienceCount(), c.getDescription(), null
                ))
                .collect(Collectors.toList());
    }

    private List<CourseResDTO.Summary> searchByDistance(String keyword, Double lat, Double lng) {
        List<Course> courses = courseRepository.findByNameContainingWithDraft(keyword);

        return courses.stream()
                .map(c -> {
                    // 각 코스의 첫 waypoint를 대표 좌표로 사용
                    DraftPoint firstPoint = c.getCourseDraft().getPoints().get(0);
                    double distance = calculateDistanceKm(lat, lng, firstPoint.getLatitude(), firstPoint.getLongitude());
                    return new CourseResDTO.Summary(
                            c.getId(), c.getName(), c.getExperienceCount(), c.getDescription(), distance
                    );
                })
                .sorted(Comparator.comparingDouble(CourseResDTO.Summary::distanceKm)) // 거리 오름차순
                .collect(Collectors.toList());
    }

    // 하버사인 공식 (두 좌표 간 실제 거리 계산)
    private  double calculateDistanceKm(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371; // 지구 반지름(km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                + Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public List<CourseResDTO.Summary> getByLocation(Double lat, Double lng, Double radiusKm) {
        // 위경도 1도 ≈ 111km 근사치로 Bounding Box 계산
        double delta = radiusKm / 111.0;
        double minLat = lat - delta, maxLat = lat + delta;
        double minLng = lng - delta, maxLng = lng + delta;

        return courseRepository.findByLocation(minLat, maxLat, minLng, maxLng).stream()
                .map(c -> new CourseResDTO.Summary(
                        c.getId(), c.getName(), c.getExperienceCount(), c.getDescription(), null
                ))
                .collect(Collectors.toList());
    }

    public void updateCourse(Long courseId, com.example.rundrawbe.domain.course.dto.CourseReqDTO.UpdateCourse request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다: " + courseId));

        // 만약 levelTagName으로 LevelTag를 찾아와야 한다면 여기서 조회 로직을 추가할 수 있어.
        // 예: LevelTag levelTag = levelTagRepository.findByLevelType(LevelType.valueOf(request.levelTagName())).orElse(null);

        // 현재는 이름과 설명 위주로 수정하는 예시
        course.updateCourseInfo(request.name(), request.description(), null);
    }
}
