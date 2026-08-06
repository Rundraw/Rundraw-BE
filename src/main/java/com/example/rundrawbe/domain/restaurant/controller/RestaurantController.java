package com.example.rundrawbe.domain.restaurant.controller;

import com.example.rundrawbe.domain.ranking.exception.code.RankingSuccessCode;
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
}