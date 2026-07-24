package com.example.rundrawbe.global.security.dto;

import com.example.rundrawbe.domain.member.enums.SocialType;

public interface OAuthDTO {
    SocialType getSocialType();
    String getSocialUid();
    String getSocialEmail();
    String getName();
}
