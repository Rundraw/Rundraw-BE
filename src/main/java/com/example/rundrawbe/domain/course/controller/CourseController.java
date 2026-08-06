package com.example.rundrawbe.domain.course.controller;

import com.example.rundrawbe.domain.course.dto.CourseReqDTO;
import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public CourseResDTO.Detail getDetail(@PathVariable Long courseId) {
        return courseService.getDetail(courseId);
    }

    @GetMapping("/search")
    public List<CourseResDTO.Summary> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "popular") String sort,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng
    ) {
        return courseService.search(keyword, sort, lat, lng);
    }

    @GetMapping("/")
    public List<CourseResDTO.Summary> getByLocation(
            @RequestParam Double lat, @RequestParam Double lng, @RequestParam Double radius
    ) {
        return courseService.getByLocation(lat, lng, radius);
    }

    // 코스 이름, 설명, 난이도 수정 API
    @PatchMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(
            @PathVariable Long courseId,
            @RequestBody CourseReqDTO.UpdateCourse request
    ) {
        courseService.updateCourse(courseId, request);
        return ResponseEntity.ok().build();
    }





}
