package com.example.rundrawbe.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

}