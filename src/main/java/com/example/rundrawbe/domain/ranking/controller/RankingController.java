package com.example.rundrawbe.domain.ranking.controller;

import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.exception.code.RankingErrorCode;
import com.example.rundrawbe.domain.ranking.exception.code.RankingSuccessCode;
import com.example.rundrawbe.domain.ranking.repository.CommentRepository;
import com.example.rundrawbe.domain.ranking.service.RankingService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import com.example.rundrawbe.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
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



}
