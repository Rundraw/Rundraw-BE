package com.example.rundrawbe.domain.restaurant.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class RestaurantException extends ProjectException {
    public RestaurantException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
