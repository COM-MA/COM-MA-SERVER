package com.example.comma.domain.fairytale.dto;

public record FairytaleResponseDto(
        Long id,
        String title,
        String imgaeUrl,
        String recommendImageUrl,
        String channelName,
        String year,
        String time,
        String link,
        String description,
        Boolean subtitleTag,
        Boolean signTag
) {
}
