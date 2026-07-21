package com.example.rundrawbe.domain.mypage.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class MypageException extends ProjectException {
    public MypageException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
