package com.example.rundrawbe.domain.auth.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE404_1", "해당 코스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
