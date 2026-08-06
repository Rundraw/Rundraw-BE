package com.example.rundrawbe.domain.restaurant.dto;


public class RestaurantReqDTO {

    public record CreateRestaurant(
            String restaurantName,
            String placeId,
            Double longitude,
            Double latitude
    ){}
}
