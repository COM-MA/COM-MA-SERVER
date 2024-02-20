package com.example.comma.domain.user.controller;

import ch.qos.logback.core.net.HardenedObjectInputStream;
import com.example.comma.domain.card.dto.response.CardResponseDto;
import com.example.comma.domain.card.service.CardService;
import com.example.comma.domain.fairytale.dto.Top2FairytaleResponseDto;
import com.example.comma.domain.fairytale.service.FairytaleService;
import com.example.comma.domain.user.dto.response.HomepageResponseDto;
import com.example.comma.domain.user.service.UserService;
import com.example.comma.global.common.SuccessResponse;
import com.example.comma.domain.user.dto.response.UserTokenResponseDto;
import com.example.comma.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final FairytaleService fairytaleService;

    // 임시 발급 API 입니다. 추후 로그인 기능이 완성되면 삭제할 예정입니다
    @PostMapping("/token/{userId}")
    public ResponseEntity<SuccessResponse<?>> getToken(@PathVariable(name ="userId" ) Long userId) {
        final UserTokenResponseDto userTokenResponseDto = userService.getToken(userId);
        return SuccessResponse.created(userTokenResponseDto);
    }

    @PostMapping("/{nickname}")
    public ResponseEntity<SuccessResponse<?>> registerNickname(@UserId Long userId, @PathVariable(name = "nickname") String nickname) {
        userService.registerNickname(userId, nickname);
        return SuccessResponse.created(null);
    }

    @GetMapping("/home")
    public ResponseEntity<SuccessResponse<?>> getHome(@UserId Long userId) {
        final HomepageResponseDto homepageResponseDto = userService.getHome(userId);
        final List<CardResponseDto> cardResponseDto = cardService.getTop5Cards(userId);
        final List<Top2FairytaleResponseDto> top2FairytaleResponseDto = fairytaleService.getTop2Fairytale();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("top2Fairytales", top2FairytaleResponseDto);
        responseData.put("top5Cards", cardResponseDto);
        responseData.put("home", homepageResponseDto);
        return SuccessResponse.ok(responseData);
    }



}
