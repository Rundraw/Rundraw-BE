package com.example.rundrawbe.domain.restaurant.service;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.converter.RankingConverter;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import com.example.rundrawbe.domain.ranking.exception.RankingException;
import com.example.rundrawbe.domain.ranking.exception.code.RankingErrorCode;
import com.example.rundrawbe.domain.restaurant.converter.RestaurantConverter;
import com.example.rundrawbe.domain.restaurant.dto.RestaurantReqDTO;
import com.example.rundrawbe.domain.restaurant.dto.RestaurantResDTO;
import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import com.example.rundrawbe.domain.restaurant.exception.RestaurantException;
import com.example.rundrawbe.domain.restaurant.exception.code.RestaurantErrorCode;
import com.example.rundrawbe.domain.restaurant.repository.CourseRestaurantRepository;
import com.example.rundrawbe.domain.restaurant.repository.RestaurantRepository;
import com.example.rundrawbe.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CourseRestaurantRepository courseRestaurantRepository;
    private final CourseRepository courseRepository;

    public List<RestaurantResDTO.SearchRestaurant> searchRestaurant(String search) {
        if (search == null || search.trim().isEmpty()) {
            return List.of(
                    RestaurantResDTO.SearchRestaurant.builder()
                            .restaurantCourseId(null)
                            .build()
            );
        }
        String keyword = search.trim();
        return courseRestaurantRepository.findAllByDeletedAtIsNull()
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

    public List<RestaurantResDTO.SearchRestaurant> getRestaurant() {
        return courseRestaurantRepository.findAllByDeletedAtIsNull()
                .stream()
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

    // 맛집 삭제
    public Object deleteRestaurant(Long courseRestaurantId, Member member) {
        CourseRestaurant courseRestaurant = courseRestaurantRepository.findById(courseRestaurantId)
                .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND));
        Course course = courseRepository.findById(courseRestaurant.getCourse().getId())
                .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.COURSE_NOT_FOUND));
        courseRestaurant.deleteRestaurant();
        return courseRestaurant.getId();
    }

    // 맛집 생성
    public Object createRestaurant(RestaurantReqDTO.CreateRestaurant dto, Long courseId, Member member) {
        CourseRestaurant courseRestaurant;
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.COURSE_NOT_FOUND));
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByPlaceId(dto.placeId());
        if(restaurantOpt.isPresent()){
            Restaurant restaurant = restaurantRepository.findByPlaceId(dto.placeId())
                    .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND));
            if(courseRestaurantRepository.existsByCourseIdAndRestaurantId(course.getId(), restaurant.getId())){
                throw new RestaurantException(RestaurantErrorCode.RESTAURANT_ALREADY_CREATED);
            }
            courseRestaurant = RestaurantConverter.toCreateCourseRestaurant(restaurant, course);

        }else {
            Restaurant restaurant = restaurantRepository.save(RestaurantConverter.toCreateRestaurant(dto));
            courseRestaurant = RestaurantConverter.toCreateCourseRestaurant(restaurant, course);
        }
        courseRestaurantRepository.save(courseRestaurant);
        return courseRestaurant.getId();
    }


    public List<RestaurantResDTO.getMap> getMap() {
        return courseRestaurantRepository.findActiveRestaurants()
                .stream()
                .map(restaurant ->
                        RestaurantResDTO.getMap.builder()
                                .id(restaurant.getId())
                                .placeId(restaurant.getPlaceId())
                                .longitude(restaurant.getLongitude())
                                .latitude(restaurant.getLatitude())
                                .build()
                )
                .distinct()
                .collect(Collectors.toList());
    }
}