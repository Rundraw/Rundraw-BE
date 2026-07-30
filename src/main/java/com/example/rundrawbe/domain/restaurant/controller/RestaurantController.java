package com.example.rundrawbe.domain.restaurant.controller;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * 맛집 목록 조회 API (지도 마커 및 리스트용)
     * GET /api/restaurants
     */
    @GetMapping
    public ResponseEntity<List<RestaurantResDTO>> getRestaurants() {
        List<RestaurantResDTO> list = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(list);
    }

    /**
     * 특정 코스별 맛집 검색/조회 API
     * GET /api/restaurants/search?courseId=1
     */
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResDTO>> getRestaurantsByCourse(@RequestParam Long courseId) {
        List<RestaurantResDTO> list = restaurantService.getRestaurantsByCourse(courseId);
        return ResponseEntity.ok(list);
    }
}