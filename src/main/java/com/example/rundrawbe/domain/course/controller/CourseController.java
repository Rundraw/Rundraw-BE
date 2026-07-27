package com.example.rundrawbe.domain.course.controller;

import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public CourseResDTO.Detail getDetail(@PathVariable Long courseId) {
        return courseService.getDetail(courseId);
    }
}
