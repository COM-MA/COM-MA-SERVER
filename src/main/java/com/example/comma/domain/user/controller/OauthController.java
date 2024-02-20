package com.example.comma.domain.user.controller;


import com.example.comma.domain.user.dto.response.LoginResponseDto;
import com.example.comma.domain.user.dto.response.UserInfoResponseDto;
import com.example.comma.domain.user.service.OauthService;
import com.example.comma.domain.user.service.UserService;
import com.example.comma.global.common.SuccessResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;

    @GetMapping("/code/{registrationId}")
    ResponseEntity<SuccessResponse<?>> googleLogin(@RequestParam (name = "code") String code, @PathVariable (name = "registrationId") String registrationId) {

        Long userId = oauthService.socialLogin(code, registrationId);
        String accessToken = userService.issueNewAccessToken(userId);
        String randomNickname = userService.generateNickname();

        LoginResponseDto response = new LoginResponseDto(accessToken, randomNickname);

        return SuccessResponse.ok(response);
    }
}