package com.example.rundrawbe.domain.home.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HomeErrorCode implements BaseErrorCode {

    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE404_1", "해당 코스를 찾을 수 없습니다.");
    // 위는 예시로 이런식으로 에러 코드만들어서 사용하시면 됩니다!

    private final HttpStatus status;
    private final String code;
    private final String message;
}
