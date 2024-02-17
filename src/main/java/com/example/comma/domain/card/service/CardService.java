package com.example.comma.domain.card.service;

import com.example.comma.domain.card.dto.response.CardImageResponseDto;
import com.example.comma.domain.card.dto.response.CardResponseDto;
import com.example.comma.domain.card.dto.response.CorrectCardResponseDto;
import com.example.comma.domain.card.dto.response.WrongCardResponseDto;
import com.example.comma.domain.card.entity.Card;
import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.card.repository.CardRepository;
import com.example.comma.domain.card.repository.UserCardRepository;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.global.error.ErrorCode;
import com.example.comma.global.error.exception.EntityNotFoundException;
import com.example.comma.global.error.exception.InvalidValueException;
import com.example.comma.global.error.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
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

        if (userCardRepository.existsByUserAndCard(user, card)) {
            throw new ConflictException(ErrorCode.USER_CARD_ALREADY_EXISTS);
        }

        UserCard userCard = new UserCard(user, card, false, true);

        userCardRepository.save(userCard);
    }

    @Transactional
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

    public List<CardResponseDto> getAlphabetCard(Long userId) {
        List<UserCard> userCards = userCardRepository.findUserCardByUserIdOrderByCardNameAsc(userId);
        return convertToCardResponseDtos(userCards);
    }

    private List<CardResponseDto> convertToCardResponseDtos(List<UserCard> userCards) {
        return userCards.stream()
                .map(userCard -> {
                    Card card = userCard.getCard();
                    return new CardResponseDto(userCard.getId(), card.getName(), card.getCardImageUrl(), card.getSignImageUrl());
                })
                .collect(Collectors.toList());
    }


    public void deleteCard(Long userId, Long userCardId) {

        UserCard userCard = (UserCard) userCardRepository.findUserCardByUserIdAndCardId(userId, userCardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_CARD_NOT_FOUND));

        userCardRepository.delete(userCard);
    }

    public CardResponseDto getCardDetail(Long userCardId) {

        UserCard userCard = userCardRepository.findById(userCardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_CARD_NOT_FOUND));

        Card card = userCard.getCard();
        return new CardResponseDto(card.getId(), card.getName(), card.getCardImageUrl(), card.getSignImageUrl());
    }

    public WrongCardResponseDto getRandomQuizCard(Long userCardId) {
        UserCard userCard = userCardRepository.findById(userCardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_CARD_NOT_FOUND));

        List<UserCard> remainUserCards = userCardRepository.findUserCardByUserIdAndCardIdNot(userCard.getUser().getId(), userCardId);

        if (remainUserCards.size() <= 1) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }

        int randomIndex = new Random().nextInt(remainUserCards.size());
        UserCard randomUserCard = remainUserCards.get(randomIndex);
        Card randomCard = randomUserCard.getCard();

        return new WrongCardResponseDto(randomCard.getName(), randomCard.getCardImageUrl(), randomCard.getSignImageUrl());
    }

    public CorrectCardResponseDto getQuizCard(Long userCardId) {
        UserCard userCard = userCardRepository.findById(userCardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_CARD_NOT_FOUND));

        Card card = userCard.getCard();
        return new CorrectCardResponseDto(card.getName(), card.getCardImageUrl(), card.getSignImageUrl());
    }

    @Transactional
    public void updateQuizParticipate(Long userCardId) {
        UserCard userCard = userCardRepository.findById(userCardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_CARD_NOT_FOUND));

        userCard.setQuizParticipation(true);
    }
}
