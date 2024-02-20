package com.example.comma.domain.user.dto.response;

public record UserInfoResponseDto(
        Long id,
        String socialId,
        String name,
        String email,
        String profileImage
) {
}
