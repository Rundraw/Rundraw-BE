package com.example.rundrawbe.domain.mypage.controller;

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.mypage.converter.MypageConverter;
import com.example.rundrawbe.domain.mypage.dto.MypageReqDTO;
import com.example.rundrawbe.domain.mypage.dto.MypageResDTO;
import com.example.rundrawbe.domain.mypage.exception.code.MypageSuccessCode;
import com.example.rundrawbe.domain.mypage.service.MypageService;
import com.example.rundrawbe.global.security.entity.AuthMember;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/me")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/comment")
    public ApiResponse<MypageResDTO.CommentListDTO> getMyComments(@AuthenticationPrincipal AuthMember authMember) {
        Long memberId = authMember.getMember().getId();
        var comments = mypageService.getMyComments(memberId);
        return ApiResponse.onSuccess(MypageSuccessCode.COMMENT_LIST_SUCCESS, MypageConverter.toCommentListDTO(comments));
    }

    @GetMapping("/scarppedcourse")
    public ApiResponse<MypageResDTO.ScrapCourseListDTO> getMyScrapCourses(@AuthenticationPrincipal AuthMember authMember) {
        Long memberId = authMember.getMember().getId();
        var scraps = mypageService.getMyScrapCourses(memberId);
        return ApiResponse.onSuccess(MypageSuccessCode.SCRAP_COURSE_LIST_SUCCESS, MypageConverter.toScrapCourseListDTO(scraps));
    }

    @GetMapping("/course")
    public ApiResponse<MypageResDTO.CourseRecordListDTO> getMyCourseRecords(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "false") boolean completedOnly) {
        Long memberId = authMember.getMember().getId();
        var records = mypageService.getMyCourseRecords(memberId, completedOnly);
        return ApiResponse.onSuccess(MypageSuccessCode.COURSE_RECORD_LIST_SUCCESS, MypageConverter.toCourseRecordListDTO(records));
    }

    @GetMapping("/draft/course")
    public ApiResponse<MypageResDTO.DraftCourseListDTO> getMyDraftCourses(@AuthenticationPrincipal AuthMember authMember) {
        Long memberId = authMember.getMember().getId();
        var drafts = mypageService.getMyDraftCourses(memberId);
        return ApiResponse.onSuccess(MypageSuccessCode.DRAFT_COURSE_LIST_SUCCESS, MypageConverter.toDraftCourseListDTO(drafts));
    }

    @GetMapping("/draft/courses/{draftCourseId}")
    public ApiResponse<MypageResDTO.DraftCourseDetailDTO> getMyDraftCourseDetail(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long draftCourseId) {
        Long memberId = authMember.getMember().getId();
        CourseDraft draft = mypageService.getMyDraftCourseDetail(memberId, draftCourseId);
        List<DraftPoint> points = mypageService.getDraftPoints(draftCourseId);
        return ApiResponse.onSuccess(MypageSuccessCode.DRAFT_COURSE_DETAIL_SUCCESS, MypageConverter.toDraftCourseDetailDTO(draft, points));
    }

    @PatchMapping("/draft/courses/{draftCourseId}")
    public ApiResponse<MypageResDTO.DraftCourseDTO> updateDraftCourseName(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long draftCourseId,
            @RequestBody MypageReqDTO.UpdateDraftCourseReq request) {
        Long memberId = authMember.getMember().getId();
        CourseDraft draft = mypageService.updateDraftCourseName(memberId, draftCourseId, request.getName());
        return ApiResponse.onSuccess(MypageSuccessCode.DRAFT_COURSE_UPDATE_SUCCESS, MypageConverter.toDraftCourseDTO(draft));
    }

    @DeleteMapping("/draft/courses/{draftCourseId}")
    public ApiResponse<Void> deleteDraftCourse(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long draftCourseId) {
        Long memberId = authMember.getMember().getId();
        mypageService.deleteDraftCourse(memberId, draftCourseId);
        return ApiResponse.onSuccess(MypageSuccessCode.DRAFT_COURSE_DELETE_SUCCESS, null);
    }
}