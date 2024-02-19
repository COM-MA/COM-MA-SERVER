package com.example.comma.domain.user.service;

import com.example.comma.domain.card.dto.response.CardResponseDto;
import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.card.repository.UserCardRepository;
import com.example.comma.domain.card.service.CardService;
import com.example.comma.domain.fairytale.entity.UserFairytale;
import com.example.comma.domain.fairytale.repository.UserFairytaleRepository;
import com.example.comma.domain.user.dto.response.HomepageResponseDto;
import com.example.comma.domain.user.dto.response.UserTokenResponseDto;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.global.config.auth.jwt.JwtProvider;
import com.example.comma.global.error.ErrorCode;
import com.example.comma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final UserFairytaleRepository userFairytaleRepository;
    public UserTokenResponseDto getToken(Long memberId) {
        String accessToken = issueNewAccessToken(memberId);
        String refreshToken = issueNewRefreshToken(memberId);
        return new UserTokenResponseDto(accessToken, refreshToken);
    }

    private String issueNewAccessToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, true);
    }

    private String issueNewRefreshToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, false);
    }

    @Transactional
    public void registerNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        user.setNickname(nickname);
        userRepository.save(user);
    }

    public HomepageResponseDto getHome(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<UserCard> userCards = userCardRepository.findByUser(user);

        boolean isQuizParticipated = userCards.stream()
                .anyMatch(userCard -> Boolean.TRUE.equals(userCard.getQuizParticipation()));

        boolean isWordRegistered = userCards.stream()
                .anyMatch(userCard -> Boolean.TRUE.equals(userCard.getCardRegistration()));

        List<UserFairytale> userFairytales = userFairytaleRepository.findByUser(user);

        boolean isFairyTalePlayed = userFairytales.stream()
                .anyMatch(userFairytale -> Boolean.TRUE.equals(userFairytale.getFairytalePlay()));

        return new HomepageResponseDto(
                user.getNickname(),
                isWordRegistered,
                isQuizParticipated,
                isFairyTalePlayed
        );
    }


}
