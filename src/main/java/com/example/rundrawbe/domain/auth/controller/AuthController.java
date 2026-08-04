package com.example.rundrawbe.domain.auth.controller;

import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.member.exception.code.MemberSuccessCode;
import com.example.rundrawbe.domain.member.service.MemberService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import com.example.rundrawbe.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("logout")
    public ApiResponse<Object> logout(
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = MemberSuccessCode.MEMBER_LOGOUT_SUCCESS;
        return ApiResponse.onSuccess(code, memberService.logout(authMember.getMember()));
    }

}
