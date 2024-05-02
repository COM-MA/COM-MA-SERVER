package com.example.comma.domain.user.dto.response;

import com.example.comma.domain.user.entity.Emotion;
import lombok.Builder;

@Builder
public record EmotionResponse(
        String parentEmotion,
        String childEmotion
) {

}
