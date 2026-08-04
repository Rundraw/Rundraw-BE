package com.example.rundrawbe.domain.restaurant.service;

import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
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
     * 맛집 저장 기능 (프론트엔드에서 보낸 구글 식당 정보를 DB에 저장)
     */
    @Transactional
    public Long saveRestaurant(Long courseId, RestaurantResDTO dto) {
        // DTO에 정의한 toEntity() 메서드를 이용해 안전하게 엔티티 생성
        Restaurant restaurant = dto.toEntity();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // 만약 코스와 맛집을 연결하는 중간 테이블 저장이 필요하다면 여기에 추가 로직을 작성하시면 됩니다.

        return savedRestaurant.getId();
    }

    /**
     * 맛집 삭제 기능
     */
    @Transactional
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 맛집이 존재하지 않습니다. id = " + id));

        restaurantRepository.delete(restaurant);
    }
}