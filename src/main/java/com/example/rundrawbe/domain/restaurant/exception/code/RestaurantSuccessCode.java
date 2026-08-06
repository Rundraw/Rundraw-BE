package com.example.rundrawbe.domain.restaurant.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RestaurantSuccessCode implements BaseSuccessCode {

    RESTAURANT_GET_SUCCESS(HttpStatus.OK, "RESTAURANT200_1", "성공적으로 맛집을 조회했습니다."),
    RESTAURANT_UPDATE_SUCCESS(HttpStatus.OK, "RESTAURANT200_2", "성공적으로 맛집을 수정했습니다."),
    RESTAURANT_DELETE_SUCCESS(HttpStatus.OK, "RESTAURANT200_3", "성공적으로 맛집을 삭제했습니다."),
    RESTAURANT_CREATE_SUCCESS(HttpStatus.CREATED, "RESTAURANT201_1", "성공적으로 맛집을 생성했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
