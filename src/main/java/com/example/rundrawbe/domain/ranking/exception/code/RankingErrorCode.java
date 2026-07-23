package com.example.rundrawbe.domain.ranking.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RankingErrorCode implements BaseErrorCode {

    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE404_1", "해당 코스를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404_1", "해당 댓글를 찾을 수 없습니다."),
    COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "COMMENT403_1", "해당 댓글에 접근할 권한이 없습니다."),
    COMMENT_ALREADY_DELETED(HttpStatus.NOT_FOUND, "COMMENT404_2", "이미 삭제된 댓글입니다."),
    QUERY_NOT_VALID(HttpStatus.NOT_FOUND, "QUERY404_1", "해당 쿼리을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
