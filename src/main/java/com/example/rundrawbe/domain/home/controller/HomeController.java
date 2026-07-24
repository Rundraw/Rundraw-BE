package com.example.rundrawbe.domain.home.controller;

import com.example.rundrawbe.domain.home.dto.HomeResDTO;
import com.example.rundrawbe.domain.home.exception.code.HomeSuccessCode;
import com.example.rundrawbe.domain.home.service.HomeService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    /**
     * 내 경로 상위 3개 조회하기
     * GET /api/user/me/course
     * TODO: authMember.getMember().getId() 실제 getter명이 다르면 이 부분만 맞춰주세요
     */
    @GetMapping("/user/me/course")
    public ApiResponse<List<HomeResDTO.MyCourseDTO>> getMyCourses(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "3") int size) {
        List<HomeResDTO.MyCourseDTO> result = homeService.getMyCourses(authMember.getMember().getId(), size);
        return ApiResponse.onSuccess(HomeSuccessCode.MY_COURSE_VIEW_SUCCESS, result);
    }

    /**
     * 저장한 맛집 상위 5개 조회
     * GET /api/user/me/restaurant
     */
    @GetMapping("/user/me/restaurant")
    public ApiResponse<List<HomeResDTO.MyRestaurantDTO>> getMySavedRestaurants(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "5") int size) {
        List<HomeResDTO.MyRestaurantDTO> result = homeService.getMySavedRestaurants(authMember.getMember().getId(), size);
        return ApiResponse.onSuccess(HomeSuccessCode.MY_RESTAURANT_VIEW_SUCCESS, result);
    }

    /**
     * 저장된 인기 GPS 5개 조회 (로그인 여부 상관없이 공개 데이터)
     * GET /api/gpsart
     */
    @GetMapping("/gpsart")
    public ApiResponse<List<HomeResDTO.GpsArtDTO>> getPopularGpsArt(
            @RequestParam(defaultValue = "5") int size) {
        List<HomeResDTO.GpsArtDTO> result = homeService.getPopularGpsArt(size);
        return ApiResponse.onSuccess(HomeSuccessCode.GPS_ART_VIEW_SUCCESS, result);
    }
}