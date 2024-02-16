package com.example.comma.domain.card.service;

import com.example.comma.domain.card.dto.response.CardImageResponseDto;
import com.example.comma.domain.card.dto.response.CardResponseDto;
import com.example.comma.domain.card.entity.Card;
import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.card.repository.CardRepository;
import com.example.comma.domain.card.repository.UserCardRepository;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.global.error.ErrorCode;
import com.example.comma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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

    public List<CardResponseDto> getLatestCard(Long userId) {
        List<UserCard> userCards = userCardRepository.findUserCardByUserIdOrderByCreateDateDesc(userId);
        return convertToCardResponseDtos(userCards);
    }

    private List<CardResponseDto> convertToCardResponseDtos(List<UserCard> userCards) {
        return userCards.stream()
                .map(userCard -> {
                    Card card = userCard.getCard();
                    return new CardResponseDto(card.getId(), card.getName(), card.getCardImageUrl(), card.getSignImageUrl());
                })
                .collect(Collectors.toList());
    }

}
