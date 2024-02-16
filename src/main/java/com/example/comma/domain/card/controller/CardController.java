package com.example.comma.domain.card.controller;

import com.example.comma.domain.card.service.CardService;
import com.example.comma.domain.card.dto.response.CardImageResponseDto;
import com.example.comma.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}
