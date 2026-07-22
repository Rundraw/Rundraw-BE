package com.example.rundrawbe.domain.member.converter;

import com.example.rundrawbe.domain.member.dto.MemberResDTO;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.global.security.dto.OAuthDTO;

public class MemberConverter {

    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .name(dto.getName())
                .socialType(dto.getSocialType())
                .socialUid(dto.getSocialUid())
                .email(dto.getSocialEmail())
                .build();
    }

    public static MemberResDTO.Login toLogin(String accessToken) {
        return new MemberResDTO.Login(accessToken);
    }
}
