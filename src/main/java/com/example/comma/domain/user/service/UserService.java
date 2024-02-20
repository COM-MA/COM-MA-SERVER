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
import java.util.Random;

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

    public String issueNewAccessToken(Long memberId) {
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

    private static final String[] ADJECTIVES = {"행복한", "밝은", "용감한", "영리한", "친절한",
            "신나는", "귀여운", "날렵한", "총명한", "명랑한",
            "따뜻한", "자유로운", "열정적인", "창의적인", "성실한",
            "유쾌한", "현명한", "도전적인", "낙천적인", "포근한"};

    private static final String[] NOUNS = {"용", "별", "탐험가", "불사조", "여행",
            "모험자", "해변", "드래곤", "비행기", "오후",
            "해적", "마법사", "코끼리", "책", "음악",
            "강", "손", "꽃", "천사", "바다"};
    public static String generateNickname() {
        String adjective = getRandomElement(ADJECTIVES);
        String noun = getRandomElement(NOUNS);
        return adjective +" "+ noun ;
    }

    private static String getRandomElement(String[] array) {
        Random random = new Random();
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
    }




}
