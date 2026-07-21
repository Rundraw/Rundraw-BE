package com.example.rundrawbe.domain.record.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecordSuccessCode implements BaseSuccessCode {

    COURSE_GET_SUCCESS(HttpStatus.OK, "COURSE200_1", "성공적으로 코스를 조회했습니다.");
    // 위는 예시로 이런식으로 에러 코드만들어서 사용하시면 됩니다!

    private final HttpStatus status;
    private final String code;
    private final String message;
}
