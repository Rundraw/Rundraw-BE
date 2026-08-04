package com.example.rundrawbe.domain.restaurant.service;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant; // 💡 Restaurant 엔티티 임포트 필요
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

    /**
     * 맛집 단건 조회 기능
     */
    public RestaurantResDTO getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 맛집이 존재하지 않습니다. id = " + id));
        return new RestaurantResDTO(restaurant);
    }

    /**
     * 맛집 삭제 기능 (데이터 변경이 일어나므로 @Transactional 추가)
     */
    @Transactional
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 맛집이 존재하지 않습니다. id = " + id));

        restaurantRepository.delete(restaurant);
    }
}