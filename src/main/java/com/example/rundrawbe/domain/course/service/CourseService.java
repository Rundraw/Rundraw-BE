package com.example.rundrawbe.domain.course.service;

import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
