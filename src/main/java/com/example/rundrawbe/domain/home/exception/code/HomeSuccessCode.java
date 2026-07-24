package com.example.rundrawbe.domain.home.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HomeSuccessCode implements BaseSuccessCode {

    MY_COURSE_VIEW_SUCCESS(HttpStatus.OK, "HOME200_1", "내 경로 조회에 성공했습니다."),
    MY_RESTAURANT_VIEW_SUCCESS(HttpStatus.OK, "HOME200_2", "저장한 맛집 조회에 성공했습니다."),
    GPS_ART_VIEW_SUCCESS(HttpStatus.OK, "HOME200_3", "인기 GPS 아트 조회에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}