package com.example.rundrawbe.domain.restaurant.service;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
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

    public List<RestaurantResDTO.SearchRestaurant> searchRestaurant(String search) {
        if (search == null || search.trim().isEmpty()) {
            return List.of(
                    RestaurantResDTO.SearchRestaurant.builder()
                            .restaurantCourseId(null)
                            .build()
            );
        }
        String keyword = search.trim();
        return courseRestaurantRepository.findAll()
                .stream()
                .filter(courseRestaurant ->
                        courseRestaurant.getCourse().getName()
                                .contains(keyword)
                )
                .map(courseRestaurant ->
                        RestaurantResDTO.SearchRestaurant.builder()
                                .restaurantCourseId(courseRestaurant.getId())
                                .restaurantName(courseRestaurant.getRestaurant().getRestaurantName())
                                .courseName(courseRestaurant.getCourse().getName())
                                .placeId(courseRestaurant.getRestaurant().getPlaceId())
                                .latitude(courseRestaurant.getRestaurant().getLatitude())
                                .longitude(courseRestaurant.getRestaurant().getLongitude())
                                .build()
                )
                .collect(Collectors.toList());
    }
}