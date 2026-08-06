package com.example.rundrawbe.domain.restaurant.dto;

import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResDTO {
    private Long id;
    private String restaurantName;
    private String description;
    private Double latitude;
    private Double longitude;
    private String placeId;
    private String url;

    // 엔티티를 받아서 DTO로 변환하는 생성자
    public RestaurantResDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.description = restaurant.getDescription();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.placeId = restaurant.getPlaceId();
        this.url = restaurant.getUrl();
    }

    public Restaurant toEntity() {
        return new Restaurant(
                this.restaurantName,
                this.description,
                this.latitude,
                this.longitude,
                this.placeId,
                this.url
        );
    }
}