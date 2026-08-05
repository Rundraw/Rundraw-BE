package com.example.rundrawbe.domain.course.controller;

import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.dto.NavigationResDTO;
import com.example.rundrawbe.domain.course.service.CourseDraftService;
import com.example.rundrawbe.domain.course.service.CourseService;
import com.example.rundrawbe.domain.course.service.NavigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;
    private final NavigationService navigationService;
    private final CourseDraftService courseDraftService;

    // CourseController.java 에 추가
    @GetMapping("/draft/{courseDraftId}")
    public CourseResDTO.DraftDetail getCourseDraft(@PathVariable Long courseDraftId) {
        return courseDraftService.getDraftDetail(courseDraftId);   // ← 반환 타입 수정
    }

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

    @GetMapping("/{courseId}/navigation")
    public NavigationResDTO.NavigationList getNavigation(@PathVariable Long courseId) {
        return navigationService.generateInstructions(courseId);
    }

    @GetMapping("/draft/{courseDraftId}/navigation")
    public NavigationResDTO.NavigationList getNavigationFromDraft(@PathVariable Long courseDraftId) {
        return navigationService.generateInstructionsFromDraft(courseDraftId);
    }
}
