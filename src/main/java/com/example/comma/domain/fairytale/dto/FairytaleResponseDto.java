package com.example.comma.domain.fairytale.dto;

public record FairytaleResponseDto(
        Long id,
        String title,
        String imgaeUrl,
        String channelName,
        String year,
        String time,
        String link,
        Boolean subtitleTag,
        Boolean signTag
) {
}
