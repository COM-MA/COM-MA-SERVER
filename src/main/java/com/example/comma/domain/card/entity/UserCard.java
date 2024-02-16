package com.example.comma.domain.card.entity;

import com.example.comma.domain.user.entity.User;
import com.example.comma.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_card")
@Entity
public class UserCard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    private Boolean quizParticipation;

    private Boolean cardRegistration;

    public UserCard(User user, Card card, Boolean quizParticipation, Boolean cardRegistration) {
        this.user = user;
        this.card = card;
        this.quizParticipation = quizParticipation;
        this.cardRegistration = cardRegistration;
    }

    public void setQuizParticipation(Boolean quizParticipation) {
        this.quizParticipation = quizParticipation;
    }

    public void setCardRegistration(Boolean cardRegistration) {
        this.cardRegistration = cardRegistration;
    }
}
