package com.example.rundrawbe.domain.restaurant.service;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import com.example.rundrawbe.domain.restaurant.repository.CourseRestaurantRepository;
import com.example.rundrawbe.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CourseRestaurantRepository courseRestaurantRepository;

    public List<RestaurantResDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantResDTO::new)
                .collect(Collectors.toList());
    }

    public List<RestaurantResDTO> getRestaurantsByCourse(Long courseId) {
        List<CourseRestaurant> courseRestaurants = courseRestaurantRepository.findByCourseId(courseId);

        return courseRestaurants.stream()
                .map(cr -> new RestaurantResDTO(cr.getRestaurant()))
                .collect(Collectors.toList());
    }
}