package com.example.rundrawbe.domain.restaurant.converter;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.restaurant.dto.RestaurantReqDTO;
import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import org.springframework.stereotype.Component;

public class RestaurantConverter {

    public static CourseRestaurant toCreateCourseRestaurant(
            Restaurant restaurant, Course course
    ){
        return CourseRestaurant.builder()
                .restaurant(restaurant)
                .course(course)
                .build();
    }


    public static Restaurant toCreateRestaurant(RestaurantReqDTO.CreateRestaurant dto) {
        return Restaurant.builder()
                .restaurantName(dto.restaurantName())
                .longitude(dto.longitude())
                .latitude(dto.latitude())
                .placeId(dto.placeId())
                .build();
    }
}