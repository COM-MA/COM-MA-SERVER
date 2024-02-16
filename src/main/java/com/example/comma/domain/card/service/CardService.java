package com.example.comma.domain.card.service;

import com.example.comma.global.error.ErrorCode;
import com.example.comma.domain.card.entity.Card;
import com.example.comma.domain.card.dto.response.CardImageResponseDto;
import com.example.comma.domain.card.repository.CardRepository;
import com.example.comma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class CardService {

    private final CardRepository cardRepository;

    public CardImageResponseDto getCardImage(String name) {
        System.out.println("name = " + name);
        Card card = cardRepository.findCardByName(name)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CARD_NOT_FOUND));
        return new CardImageResponseDto(card.getCardImageUrl(), card.getSignImageUrl());
    }
}
