package com.example.rundrawbe.domain.course.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class CourseException extends ProjectException {
    public CourseException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
