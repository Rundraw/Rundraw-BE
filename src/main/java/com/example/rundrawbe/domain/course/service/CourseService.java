package com.example.rundrawbe.domain.course.service;

import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                course.getCourseId(),
                course.getName(),
                course.getExperienceCount(),
                course.getDescription(),
                course.getLevelTag() != null ? course.getLevelTag().getLevelType().name() : null
        );
    }

    public List<CourseResDTO.Summary> search(String keyword) {
        return courseRepository.findByNameContaining(keyword).stream()
                .map(c -> new CourseResDTO.Summary(
                        c.getCourseId(), c.getName(), c.getExperienceCount(), c.getDescription()
                ))
                .collect(Collectors.toList());
    }

    public List<CourseResDTO.Summary> getByLocation(Double lat, Double lng, Double radiusKm) {
        // 위경도 1도 ≈ 111km 근사치로 Bounding Box 계산
        double delta = radiusKm / 111.0;
        double minLat = lat - delta, maxLat = lat + delta;
        double minLng = lng - delta, maxLng = lng + delta;

        return courseRepository.findByLocation(minLat, maxLat, minLng, maxLng).stream()
                .map(c -> new CourseResDTO.Summary(
                        c.getCourseId(), c.getName(), c.getExperienceCount(), c.getDescription()
                ))
                .collect(Collectors.toList());
    }
}
