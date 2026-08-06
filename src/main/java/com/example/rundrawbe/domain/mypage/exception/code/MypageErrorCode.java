package com.example.rundrawbe.domain.mypage.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MypageErrorCode {

    DRAFT_COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "MYPAGE4001", "존재하지 않는 코스 초안입니다."),
    DRAFT_COURSE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "MYPAGE4031", "본인이 그린 코스만 수정/삭제할 수 있습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MYPAGE4002", "존재하지 않는 댓글입니다."),
    SCRAP_COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "MYPAGE4003", "존재하지 않는 스크랩 코스입니다."),
    DRAFT_COURSE_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "MYPAGE4004", "완주한 코스만 공유할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}