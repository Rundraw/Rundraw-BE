package com.example.rundrawbe.domain.ranking.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RankingSuccessCode implements BaseSuccessCode {

    COMMENT_GET_SUCCESS(HttpStatus.OK, "COMMENT200_1", "성공적으로 댓글을 조회했습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "COMMENT200_2", "성공적으로 댓글을 수정했습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "COMMENT200_3", "성공적으로 댓글을 삭제했습니다."),
    COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "COMMENT201_1", "성공적으로 댓글을 생성했습니다."),
    LIKE_DELETE_SUCCESS(HttpStatus.OK, "LIKE200_3", "성공적으로 좋아요를 삭제했습니다."),
    LIKE_CREATE_SUCCESS(HttpStatus.CREATED, "LIKE201_1", "성공적으로 좋아요를 생성했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
