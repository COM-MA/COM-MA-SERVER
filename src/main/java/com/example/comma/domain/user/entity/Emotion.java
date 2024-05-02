package com.example.comma.domain.user.entity;

public enum Emotion {
    SOSO("그저 그래요"),
    ANGRY("화나요"),
    SAD("슬퍼요"),
    PEACEFUL("평온해요"),
    DESPRESS("우울해요"),
    HAPPY("행복해요"),
    FULLFIILED("뿌듯해요"),
    ANXIOUS("불안해요"),
    NONE("모르겠어요");

    private final String koreanEmotion;

    Emotion(String koreanEmotion) {
        this.koreanEmotion = koreanEmotion;
    }

    public String getKoreanEmotion() {
        return koreanEmotion;
    }
}
