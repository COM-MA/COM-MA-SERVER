package com.example.comma.domain.card.controller;

import com.example.comma.domain.card.dto.request.CardInfoRequest;
import com.example.comma.domain.card.dto.response.*;
import com.example.comma.domain.card.service.CardService;
import com.example.comma.domain.external.service.GeminiService;
import com.example.comma.domain.external.service.ImageCrawlerService;
import com.example.comma.global.common.SuccessResponse;
import com.example.comma.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/card")
@RestController
public class CardController {
    private final CardService cardService;
    private final GeminiService geminiService;
    private final ImageCrawlerService imageCrawlerService;


    //단어 리스트 검색
    @GetMapping("/search-word")
    public ResponseEntity<SuccessResponse<?>> getSearchList(@RequestParam(name = "searchWord") String searchWord) throws IOException {
        List<String> searchResults = imageCrawlerService.crawlSearchList(searchWord);
        List<DescriptionResponseDto> descriptionResponse = geminiService.generateDescriptionList(searchResults);
        return SuccessResponse.ok(descriptionResponse);
    }

    //단어 상세 정보 조회
    @GetMapping("/search-details")
    public ResponseEntity<SuccessResponse<?>> generateResponse(@RequestParam(name = "searchWord") String searchWord) throws IOException {

       //수형, 단어 이미지 생성
        List<String> signImageUrls = imageCrawlerService.crawlImageUrls(searchWord);
        byte[] mergeImages = imageCrawlerService.mergeImages(signImageUrls);
        String signImageUrl = imageCrawlerService.uploadFile(mergeImages, searchWord + ".jpg");
        String generatedImageUrl = imageCrawlerService.generateImage(searchWord);;

        //cardId 생성
        cardService.registerCard(searchWord, signImageUrl);
        Long cardId = cardService.getCardId(searchWord);

        //단어 사전 정의 생성
        List<DescriptionResponseDto> descriptionResponse = geminiService.generateDescriptionList(Collections.singletonList(searchWord));

        //수형 동작 설명
        String generatesignLanguageDescription= geminiService.generateSignDescription(searchWord);

        WordDatailsResponseDto wordDatailsResponse = new WordDatailsResponseDto(cardId, searchWord, descriptionResponse.get(0).description(),descriptionResponse.get(0).partsOfSeech(), generatedImageUrl, signImageUrl,generatesignLanguageDescription );

        return SuccessResponse.ok(wordDatailsResponse);
    }


    //UserCard 단어 카드 저장
    @PostMapping("/{cardId}")
    public ResponseEntity<SuccessResponse<?>> saveCard(@UserId Long userId, @PathVariable(name = "cardId") Long cardId, @RequestBody CardInfoRequest cardInfoRequest) {
        cardService.saveCard(userId, cardId, cardInfoRequest);
        return SuccessResponse.created(null);
    }

    //UserCard 개별 정보 조회
    @GetMapping("/my-card/{userCardId}")
    public ResponseEntity<SuccessResponse<?>> getMyCard(@PathVariable(name = "userCardId") Long userCardId) {
       MyCardResponseDto myCard = cardService.getMyCard(userCardId);
        return SuccessResponse.ok(myCard);
    }

    @GetMapping("/lastest")
    public ResponseEntity<SuccessResponse<?>> getLastestCard(@UserId Long userId) {
        List<CardResponseDto> CardImage = cardService.getLatestCard(userId);
        return SuccessResponse.ok(CardImage);
    }

    @GetMapping("/alphabet")
    public ResponseEntity<SuccessResponse<?>> getAlphabetCard(@UserId Long userId) {
        List<CardResponseDto> CardImage = cardService.getAlphabetCard(userId);
        return SuccessResponse.ok(CardImage);
    }

    @DeleteMapping("/{userCardId}")
    public ResponseEntity<SuccessResponse<?>> deleteCard(@UserId Long userId, @PathVariable(name = "userCardId") Long userCardId) {
        cardService.deleteCard(userId, userCardId);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/detail/{userCardId}")
    public ResponseEntity<SuccessResponse<?>> getCardDetail(@PathVariable(name = "userCardId") Long userCardId) {
        CardResponseDto CardImage = cardService.getCardDetail(userCardId);
        return SuccessResponse.ok(CardImage);
    }

    @PostMapping("/quiz/{userCardId}")
    public ResponseEntity<SuccessResponse<?>> getQuizCard(@UserId Long userId, @PathVariable(name = "userCardId") Long userCardId) {
        CorrectCardResponseDto correctCardImage = cardService.getQuizCard(userCardId);
        WrongCardResponseDto wrongCardImage = cardService.getRandomQuizCard(userCardId);
        cardService.updateQuizParticipate(userCardId);
        QuizResponseDto quizResponse = new QuizResponseDto(correctCardImage, wrongCardImage);
        return SuccessResponse.ok(quizResponse);
    }

}
