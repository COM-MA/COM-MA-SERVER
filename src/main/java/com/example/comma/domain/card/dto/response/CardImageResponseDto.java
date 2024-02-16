package com.example.comma.domain.card.dto.response;

import lombok.Builder;

public record CardImageResponseDto(
        String CardImageUrl,
        String SignImageUrl
) {

}
