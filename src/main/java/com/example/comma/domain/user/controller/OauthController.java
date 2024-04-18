package com.example.comma.domain.user.controller;


import com.example.comma.domain.user.dto.response.LoginResponseDto;
import com.example.comma.domain.user.dto.response.UserInfoResponseDto;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.domain.user.service.OauthService;
import com.example.comma.domain.user.service.UserService;
import com.example.comma.global.common.SuccessResponse;

import com.example.comma.global.error.ErrorCode;
import com.example.comma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/code/{registrationId}")
    ResponseEntity<SuccessResponse<?>> googleLogin(@RequestParam (name = "code") String code, @PathVariable (name = "registrationId") String registrationId) {

        Long userId  = oauthService.socialLogin(code, registrationId);
        String accessToken = userService.issueNewAccessToken(userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        String nickname = user.getNickname();
        if (nickname == null) { //
            nickname = UserService.generateNickname();
            LoginResponseDto response = new LoginResponseDto(accessToken, nickname,true);
            return SuccessResponse.ok(response);
        }
        else{
            LoginResponseDto response = new LoginResponseDto(accessToken, nickname,false);
            return SuccessResponse.ok(response);
        }
    }


}