package com.example.rundrawbe.domain.restaurant.controller;

import com.example.rundrawbe.domain.restaurant.exception.code.RestaurantSuccessCode;
import com.example.rundrawbe.domain.restaurant.service.RestaurantService;
import com.example.rundrawbe.global.apiPayload.ApiResponse;
import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
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
}