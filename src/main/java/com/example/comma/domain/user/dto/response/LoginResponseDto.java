package com.example.comma.domain.user.dto.response;

public record LoginResponseDto(
        String accessToken,
        String RandomNickname
) {
}
