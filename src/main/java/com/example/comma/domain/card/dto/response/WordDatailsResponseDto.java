package com.example.comma.domain.card.dto.response;

public record WordDatailsResponseDto(
        Long cardId,
        String word,
        String description,
        String partsOfSeech,
        String cardImageUrl,
        String signImageUrl,
        String signLanguageDescription

) {
}
