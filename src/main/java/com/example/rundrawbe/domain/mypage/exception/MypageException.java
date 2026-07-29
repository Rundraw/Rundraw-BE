package com.example.rundrawbe.domain.mypage.exception;

import com.example.rundrawbe.domain.mypage.exception.code.MypageErrorCode;
import lombok.Getter;

@Getter
public class MypageException extends RuntimeException {

    private final MypageErrorCode errorCode;

    public MypageException(MypageErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}