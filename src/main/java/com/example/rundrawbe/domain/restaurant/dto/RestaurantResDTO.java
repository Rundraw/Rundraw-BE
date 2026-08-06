package com.example.rundrawbe.domain.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantResDTO {
    @Builder
    public record SearchRestaurant(
            Long restaurantCourseId,
            String restaurantName,
            String courseName,
            String placeId,
            Double longitude,
            Double latitude
    ){}

}