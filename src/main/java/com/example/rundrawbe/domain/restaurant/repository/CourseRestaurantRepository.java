package com.example.rundrawbe.domain.restaurant.repository;

import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // 이 임포트도 필요할 수 있어!

@Repository
public interface CourseRestaurantRepository extends JpaRepository<CourseRestaurant, Long> {

    // 이 줄을 추가해 주면 돼!
    List<CourseRestaurant> findByCourseId(Long courseId);

}