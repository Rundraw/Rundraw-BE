package com.example.rundrawbe.domain.restaurant.controller;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // 이 부분에 @DeleteMapping 등이 포함됩니다.

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

    /**
     * 맛집 단건 조회 API (필요한 경우)
     * GET /api/restaurants/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResDTO> getRestaurant(@PathVariable Long id) {
        RestaurantResDTO restaurant = restaurantService.getRestaurant(id);
        return ResponseEntity.ok(restaurant);
    }

    /**
     * 맛집 삭제 API
     * DELETE /api/restaurants/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build(); // 삭제 성공 시 보통 204 No Content 반환
    }
}