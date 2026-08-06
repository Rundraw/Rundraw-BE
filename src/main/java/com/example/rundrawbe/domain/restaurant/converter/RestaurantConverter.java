package com.example.rundrawbe.domain.restaurant.converter;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantConverter {

    // 엔티티 -> DTO 변환
    public static RestaurantResDTO toRestaurantResDTO(Restaurant restaurant) {
        return new RestaurantResDTO(restaurant);
    }

    // DTO -> 엔티티 변환 (생성자를 통해 DTO의 값을 엔티티에 담아줌)
    public static Restaurant toRestaurant(RestaurantResDTO dto) {
        return new Restaurant(
                dto.getRestaurantName(),
                dto.getDescription(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getPlaceId(),
                dto.getUrl()
        );
    }
}