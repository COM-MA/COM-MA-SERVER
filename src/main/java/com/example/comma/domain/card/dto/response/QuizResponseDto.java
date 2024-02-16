package com.example.comma.domain.card.dto.response;

public record QuizResponseDto(
        CorrectCardResponseDto correctCard,
        WrongCardResponseDto wrongCard
) {
}
