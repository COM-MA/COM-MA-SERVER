package com.example.comma.domain.card.controller;

import com.example.comma.domain.card.dto.response.CardResponseDto;
import com.example.comma.domain.card.service.CardService;
import com.example.comma.domain.card.dto.response.CardImageResponseDto;
import com.example.comma.global.common.SuccessResponse;
import com.example.comma.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/card")
@RestController
public class CardController {
    private final CardService cardService;

    @GetMapping("/{name}")
    public ResponseEntity<SuccessResponse<?>> getWord(@PathVariable(name = "name") String name) {
        CardImageResponseDto CardImage = cardService.getCardImage(name);
        return SuccessResponse.ok(CardImage);
    }

    @PostMapping("/{cardId}")
    public ResponseEntity<SuccessResponse<?>> createCard(@UserId Long userId, @PathVariable(name = "cardId") Long cardId) {
        cardService.createCard(userId, cardId);
        return SuccessResponse.created(null);
    }

    @GetMapping("/lastest")
    public ResponseEntity<SuccessResponse<?>> getLastestCard(@UserId Long userId) {
        List<CardResponseDto> CardImage = cardService.getLatestCard(userId);
        return SuccessResponse.ok(CardImage);
    }

    @GetMapping("/alpabet")
    public ResponseEntity<SuccessResponse<?>> getAlphabetCard(@UserId Long userId) {
        List<CardResponseDto> CardImage = cardService.getAlphabetCard(userId);
        return SuccessResponse.ok(CardImage);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<SuccessResponse<?>> deleteCard(@UserId Long userId, @PathVariable(name = "cardId") Long cardId) {
        cardService.deleteCard(userId, cardId);
        return SuccessResponse.ok(null);
    }


}
