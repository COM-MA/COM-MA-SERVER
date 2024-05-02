package com.example.comma.domain.card.dto.response;

public record MyCardResponseDto(
        String name,
        String cardImageUrl,
        String signImageUrl,
        String signLanguageDescription
) {
}
