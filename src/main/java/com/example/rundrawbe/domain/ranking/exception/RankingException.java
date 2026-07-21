package com.example.rundrawbe.domain.ranking.exception;

import com.example.rundrawbe.global.apiPayload.code.BaseErrorCode;
import com.example.rundrawbe.global.apiPayload.exception.ProjectException;

public class RankingException extends ProjectException {
    public RankingException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
