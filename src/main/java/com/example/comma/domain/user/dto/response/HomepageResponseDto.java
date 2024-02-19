package com.example.comma.domain.user.dto.response;

import com.example.comma.domain.card.dto.response.CardResponseDto;

import java.util.List;

public record HomepageResponseDto(
        String nickname,
        Boolean isWordRegistered,
        Boolean isQuizParticipated,
        Boolean isFairyTalePlayed

) {
}
