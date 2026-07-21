package com.example.rundrawbe.domain.auth.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class AuthException extends ProjectException {
    public AuthException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
