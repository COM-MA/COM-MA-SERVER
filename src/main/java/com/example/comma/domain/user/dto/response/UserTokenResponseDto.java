package com.example.comma.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserTokenResponseDto(
        String accessToken,
        String refreshToken
) {
    public static UserTokenResponseDto of(String accessToken,
                                            String refreshToken) {
        return UserTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}