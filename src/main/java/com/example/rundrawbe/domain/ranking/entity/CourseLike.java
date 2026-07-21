package com.example.rundrawbe.domain.ranking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class CourseLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
