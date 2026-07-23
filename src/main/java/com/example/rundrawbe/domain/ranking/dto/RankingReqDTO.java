package com.example.rundrawbe.domain.ranking.dto;

public class RankingReqDTO {
    public record CreateComment (
            String comment
    ){}

    public record UpdateComment(
            String comment
    ){}
}
