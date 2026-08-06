package com.example.rundrawbe.domain.mypage.exception.code;

import com.example.rundrawbe.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MypageSuccessCode implements BaseSuccessCode {

    COMMENT_LIST_SUCCESS(HttpStatus.OK, "MYPAGE2001", "내가 쓴 댓글 목록 조회에 성공했습니다."),
    SCRAP_COURSE_LIST_SUCCESS(HttpStatus.OK, "MYPAGE2002", "스크랩한 코스 목록 조회에 성공했습니다."),
    COURSE_RECORD_LIST_SUCCESS(HttpStatus.OK, "MYPAGE2003", "코스 기록 조회에 성공했습니다."),
    DRAFT_COURSE_LIST_SUCCESS(HttpStatus.OK, "MYPAGE2004", "내가 그린 코스 목록 조회에 성공했습니다."),
    DRAFT_COURSE_DETAIL_SUCCESS(HttpStatus.OK, "MYPAGE2005", "코스 초안 상세 조회에 성공했습니다."),
    DRAFT_COURSE_UPDATE_SUCCESS(HttpStatus.OK, "MYPAGE2006", "코스 초안 수정에 성공했습니다."),
    DRAFT_COURSE_DELETE_SUCCESS(HttpStatus.OK, "MYPAGE2007", "코스 초안 삭제에 성공했습니다."),
    DRAFT_COURSE_SHARE_TOGGLE_SUCCESS(HttpStatus.OK, "MYPAGE2008", "코스 공유 상태 변경에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}