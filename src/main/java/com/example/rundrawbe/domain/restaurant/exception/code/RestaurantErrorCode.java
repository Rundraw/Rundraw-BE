package com.example.rundrawbe.domain.restaurant.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RestaurantErrorCode implements BaseErrorCode {

    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "RESTAURANT404_1", "해당 맛집를 찾을 수 없습니다."),
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE404_1", "해당 코스를 찾을 수 없습니다."),
    RESTAURANT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "RESTAURANT403_1", "해당 맛집에 접근할 권한이 없습니다."),
    RESTAURANT_ALREADY_CREATED(HttpStatus.NOT_FOUND, "RESTAURANT404_1", "이미  생성된 맛집입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
