package com.example.rundrawbe.domain.home.exception;

import com.example.rundrawbe.domain.home.exception.code.HomeErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class HomeException extends ProjectException {

    public HomeException(HomeErrorCode errorCode) {
        super(errorCode);
    }
}