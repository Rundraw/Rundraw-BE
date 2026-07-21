package com.example.rundrawbe.domain.home.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class HomeException extends ProjectException {
    public HomeException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
