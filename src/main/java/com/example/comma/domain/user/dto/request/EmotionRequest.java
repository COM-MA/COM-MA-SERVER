package com.example.comma.domain.user.dto.request;

import com.example.comma.domain.user.entity.Emotion;

public record EmotionRequest(
        Emotion parentEmotion,
        Emotion childEmotion
) {
}
