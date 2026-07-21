package com.example.rundrawbe.domain.restaurant.repository;

import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRestaurantRepository extends JpaRepository<CourseRestaurant, Long> {
}
