package com.example.rundrawbe.domain.restaurant.controller;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantReqDTO;
import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.exception.code.RestaurantSuccessCode;
import com.example.rundrawbe.domain.restaurant.service.RestaurantService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import com.example.rundrawbe.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 등록된 맛집 검색 : 코스 이름 검색
    @GetMapping("/search")
    public ApiResponse<Object> searchRestaurant(
            @RequestParam String search
    ){
        BaseSuccessCode code = RestaurantSuccessCode.RESTAURANT_GET_SUCCESS;
        return ApiResponse.onSuccess(code, restaurantService.searchRestaurant(search));
    }

    // 맛집 전체 조회
    @GetMapping()
    public ApiResponse<Object> getRestaurant(
    ){
        BaseSuccessCode code = RestaurantSuccessCode.RESTAURANT_GET_SUCCESS;
        return ApiResponse.onSuccess(code, restaurantService.getRestaurant());
    }

    // 맛집 삭제
    @DeleteMapping("/{courseRestaurantId}")
    public ApiResponse<Object> deleteRestaurant(
            @PathVariable Long courseRestaurantId,
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = RestaurantSuccessCode.RESTAURANT_DELETE_SUCCESS;
        return ApiResponse.onSuccess(code, restaurantService.deleteRestaurant(courseRestaurantId, authMember.getMember()));
    }

    // 맛집 생성
    @PostMapping("/{courseId}")
    public ApiResponse<Object> createRestaurant(
            @PathVariable Long courseId,
            @RequestBody RestaurantReqDTO.CreateRestaurant dto,
            @AuthenticationPrincipal AuthMember authMember
    ){
        BaseSuccessCode code = RestaurantSuccessCode.RESTAURANT_CREATE_SUCCESS;
        return ApiResponse.onSuccess(code, restaurantService.createRestaurant(dto, courseId, authMember.getMember()));
    }

    // 등록된 맛집 지도 가져오기
    @GetMapping("/map")
    public ApiResponse<Object> getMap(
    ){
        BaseSuccessCode code = RestaurantSuccessCode.RESTAURANT_GET_SUCCESS;
        return ApiResponse.onSuccess(code, restaurantService.getMap());
    }

}
