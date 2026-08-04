package com.example.rundrawbe.domain.restaurant.controller;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResDTO>> getRestaurants() {
        List<RestaurantResDTO> list = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResDTO>> getRestaurantsByCourse(@RequestParam Long courseId) {
        List<RestaurantResDTO> list = restaurantService.getRestaurantsByCourse(courseId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResDTO> getRestaurant(@PathVariable Long id) {
        RestaurantResDTO restaurant = restaurantService.getRestaurant(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<Void> addRestaurantToCourse(
            @PathVariable Long courseId,
            @RequestBody RestaurantResDTO requestDTO) {

        restaurantService.saveRestaurant(courseId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}