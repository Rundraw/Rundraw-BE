package com.example.rundrawbe.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    private String description;

    private Double latitude;

    private Double longitude;

    @Column(name = "place_id")
    private String placeId;

    private String url;

    public Restaurant(String restaurantName, String description, Double latitude, Double longitude, String placeId, String url) {
        this.restaurantName = restaurantName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.url = url;
    }
}