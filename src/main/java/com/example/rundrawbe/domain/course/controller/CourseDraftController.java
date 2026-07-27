package com.example.rundrawbe.domain.course.controller;

import com.example.rundrawbe.domain.course.dto.CourseReqDTO;
import com.example.rundrawbe.domain.course.dto.CourseResDTO;
import com.example.rundrawbe.domain.course.service.CourseDraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/me/draft/course")
@RequiredArgsConstructor
public class CourseDraftController {
    private final CourseDraftService courseDraftService;

    @PostMapping
    public ResponseEntity<CourseResDTO.DraftDetail> saveDraft(
            @RequestBody CourseReqDTO.CreateDraft request
    ) {
        CourseResDTO.DraftDetail result = courseDraftService.saveDraft(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
