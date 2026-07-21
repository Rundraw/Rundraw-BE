package com.example.rundrawbe.domain.draft.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class DraftException extends ProjectException {
    public DraftException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
