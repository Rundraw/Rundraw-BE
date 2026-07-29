package com.example.rundrawbe.domain.mypage.dto;

import lombok.Getter;

public class MypageReqDTO {

    @Getter
    public static class UpdateDraftCourseReq {
        private String name;
    }
}