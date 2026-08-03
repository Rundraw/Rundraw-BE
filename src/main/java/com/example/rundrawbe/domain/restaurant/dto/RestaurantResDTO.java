package com.example.rundrawbe.domain.restaurant.dto;

import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResDTO {
    private Long id;
    private String restaurantName;
    private String description;
    private Double latitude;
    private Double longitude;
    private String placeId;
    private String url;

    public RestaurantResDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.description = restaurant.getDescription();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.placeId = restaurant.getPlaceId();
        this.url = restaurant.getUrl();
    }
}