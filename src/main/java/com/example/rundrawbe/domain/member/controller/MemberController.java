package com.example.rundrawbe.domain.member.controller;

import com.example.rundrawbe.domain.member.dto.MemberReqDTO;
import com.example.rundrawbe.domain.member.exception.code.MemberSuccessCode;
import com.example.rundrawbe.domain.member.service.MemberService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import com.example.rundrawbe.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    // 닉네임 설정
    @PatchMapping("/users/me/name")
    public ApiResponse<String> updateName(
            @RequestBody @Valid MemberReqDTO.nickname dto,
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = MemberSuccessCode.MEMBER_UPDATE_SUCCESS;
        return ApiResponse.onSuccess(code, memberService.updateName(authMember.getMember().getId(), dto));
    }


}
