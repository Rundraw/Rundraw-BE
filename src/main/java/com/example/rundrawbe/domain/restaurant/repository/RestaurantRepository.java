package com.example.rundrawbe.domain.restaurant.repository;

import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}