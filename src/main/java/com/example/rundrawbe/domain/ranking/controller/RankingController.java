package com.example.rundrawbe.domain.ranking.controller;

import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.exception.code.RankingSuccessCode;
import com.example.rundrawbe.domain.ranking.service.RankingService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import com.example.rundrawbe.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RankingController {

    private final RankingService rankingService;

    // 댓글 작성
    @PostMapping("/courses/{courseId}/comments")
    public ApiResponse<Object> createComment(
            @PathVariable Long courseId,
            @RequestBody RankingReqDTO.CreateComment dto,
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = RankingSuccessCode.COMMENT_CREATE_SUCCESS;
        return ApiResponse.onSuccess(code, rankingService.createComment(courseId, dto, authMember.getMember()));
    }

    // 댓글 수정
    @PatchMapping("/courses/{courseId}/comments/{commentId}")
    public ApiResponse<Object> updateComment(
            @PathVariable Long courseId,
            @PathVariable Long commentId,
            @RequestBody RankingReqDTO.UpdateComment dto,
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = RankingSuccessCode.COMMENT_UPDATE_SUCCESS;
        return ApiResponse.onSuccess(code, rankingService.updateComment(courseId,commentId, dto, authMember.getMember()));
    }

    // 댓글 삭제
    @DeleteMapping("/courses/{courseId}/comments/{commentId}")
    public ApiResponse<Object> deleteComment(
            @PathVariable Long courseId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = RankingSuccessCode.COMMENT_DELETE_SUCCESS;
        return ApiResponse.onSuccess(code, rankingService.deleteComment(courseId,commentId, authMember.getMember()));
    }
}
