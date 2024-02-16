package com.example.comma.domain.card.dto.response;

import lombok.Builder;

@Builder
public record CardResponseDto(
        Long id,
        String name,
        String cardImageUrl,
        String signImageUrl
) {

}
