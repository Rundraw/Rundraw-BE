package com.example.rundrawbe.domain.record.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class RecordException extends ProjectException {
    public RecordException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
