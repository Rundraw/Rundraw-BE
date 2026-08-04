package com.example.rundrawbe.domain.member.entity;

import com.example.rundrawbe.domain.member.enums.SocialType;
import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String socialUid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Email
    @NotBlank
    private String email;

    // 닉네임 설정
    public void updateName(String nickname) {
        this.name = nickname;
    }

    // 유저 삭제
    public void deleteMember(Member member) {
        this.setDeletedAt(LocalDateTime.now());
    }
}
