package com.example.comma.domain.card.dto.response;

import lombok.Builder;

public record CardImageResponseDto(
        Long CardId,
        String CardImageUrl,
        String SignImageUrl
) {

}
