package com.example.comma.domain.card.service;

import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.card.repository.UserCardRepository;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.global.error.ErrorCode;
import com.example.comma.domain.card.entity.Card;
import com.example.comma.domain.card.dto.response.CardImageResponseDto;
import com.example.comma.domain.card.repository.CardRepository;
import com.example.comma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;

    public CardImageResponseDto getCardImage(String name) {
        System.out.println("name = " + name);
        Card card = cardRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CARD_NOT_FOUND));
        return new CardImageResponseDto(card.getId(), card.getCardImageUrl(), card.getSignImageUrl());
    }


    public void createCard(Long userId, Long cardId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CARD_NOT_FOUND));

        System.out.println("userId = " + userId);
        System.out.println("cardId = " + cardId);
        System.out.println("user = " + user);
        System.out.println("card = " + card);

        UserCard userCard = new UserCard(user, card, false, true);

        userCardRepository.save(userCard);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetCardRegistration() {
        userCardRepository.findAll().forEach(userCard -> {
            userCard.setQuizParticipation(false);
            userCard.setCardRegistration(false);
            userCardRepository.save(userCard);
        });
    }


}
