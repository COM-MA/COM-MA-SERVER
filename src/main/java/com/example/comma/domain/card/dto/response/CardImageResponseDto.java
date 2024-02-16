package com.example.comma.domain.card.dto.response;

import lombok.Builder;

public record CardImageResponseDto(
        Long id,
        String CardImageUrl,
        String SignImageUrl
) {

}
