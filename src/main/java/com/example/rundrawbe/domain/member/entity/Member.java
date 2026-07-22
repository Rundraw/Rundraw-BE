package com.example.rundrawbe.domain.member.entity;

import com.example.rundrawbe.domain.member.enums.SocialType;
import com.example.rundrawbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private String name;

    @NotBlank
    private String socialUid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Email
    @NotBlank
    private String email;
}
