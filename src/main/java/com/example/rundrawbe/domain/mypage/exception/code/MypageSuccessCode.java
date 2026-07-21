package com.example.rundrawbe.domain.mypage.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MypageSuccessCode implements BaseSuccessCode {

    COURSE_GET_SUCCESS(HttpStatus.OK, "COURSE200_1", "성공적으로 코스를 조회했습니다."),
    COURSE_UPDATE_SUCCESS(HttpStatus.OK, "COURSE200_2", "성공적으로 코스를 수정했습니다."),
    COURSE_DELETE_SUCCESS(HttpStatus.OK, "COURSE200_3", "성공적으로 코스를 삭제했습니다."),
    COURSE_CREATE_SUCCESS(HttpStatus.CREATED, "COURSE201_1", "성공적으로 코스를 생성했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
