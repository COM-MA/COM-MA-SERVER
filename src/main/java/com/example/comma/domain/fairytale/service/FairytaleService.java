package com.example.comma.domain.fairytale.service;

import com.example.comma.domain.fairytale.dto.FairytaleDetailResponseDto;
import com.example.comma.domain.fairytale.dto.FairytaleResponseDto;
import com.example.comma.domain.fairytale.entity.Fairytale;
import com.example.comma.domain.fairytale.entity.UserFairytale;
import com.example.comma.domain.fairytale.repository.FairytaleRepository;
import com.example.comma.domain.fairytale.repository.UserFairytaleRepository;
import com.example.comma.domain.user.entity.User;
import com.example.comma.domain.user.repository.UserRepository;
import com.example.comma.global.error.exception.EntityNotFoundException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.comma.global.error.ErrorCode.FAIRYTALE_NOT_FOUND;
import static com.example.comma.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class FairytaleService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final FairytaleRepository fairytaleRepository;
    private final UserFairytaleRepository userFairytaleRepository;
    private final UserRepository userRepository;


    public String searchVideo(Fairytale fairytale, String title, String channelName) {
        try {
            JsonFactory jsonFactory = new JacksonFactory();

            YouTube youtube = new YouTube.Builder(
                    new com.google.api.client.http.javanet.NetHttpTransport(),
                    jsonFactory,
                    request -> {})
                    .build();

            String query = title + " " + channelName;

            YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));

            search.setKey(apiKey);
            search.setQ(query);

            SearchListResponse searchResponse = search.execute();

            List<SearchResult> searchResultList = searchResponse.getItems();

            if (searchResultList != null && searchResultList.size() > 0) {
                SearchResult searchResult = searchResultList.get(0);
                String videoId = searchResult.getId().getVideoId();
                return "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<FairytaleResponseDto> getFairytale() {
        List<Fairytale> fairytaleList = fairytaleRepository.findAll();

        return fairytaleList.stream()
                .map(fairytale -> {
                    try {
                        String videolink;
                        if (fairytale.getId() == 1 || fairytale.getId() == 4) {
                            videolink = fairytale.getLink();
                        } else {
                            videolink = searchVideo(fairytale, fairytale.getTitle(), fairytale.getChannelName());
                        }

                        return new FairytaleResponseDto(
                                fairytale.getId(),
                                fairytale.getTitle(),
                                fairytale.getImgaeUrl(),
                                fairytale.getChannelName(),
                                fairytale.getYear().toString(),
                                fairytale.getTime().toString(),
                                videolink,
                                fairytale.getSubtitleTag(),
                                fairytale.getSignTag()
                        );
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }


    @Transactional
    public FairytaleDetailResponseDto getFairytaleDetail(Long userId, Long fairytaleId) {
        Fairytale fairytale = fairytaleRepository.findById(fairytaleId)
                .orElseThrow(() -> new EntityNotFoundException(FAIRYTALE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        UserFairytale existingUserFairytale = userFairytaleRepository.findByUserAndFairytale(user, fairytale);

        if (existingUserFairytale != null) {
            existingUserFairytale.setFairytalePlay(true);
            userFairytaleRepository.save(existingUserFairytale);
        } else {
            UserFairytale newUserFairytale = new UserFairytale();
            newUserFairytale.setUser(user);
            newUserFairytale.setFairytale(fairytale);
            newUserFairytale.setFairytalePlay(true);
            userFairytaleRepository.save(newUserFairytale);
        }

        String description = fairytale.getDescription();

        List<Fairytale> randomFairytaleList = fairytaleRepository.findByIdNot(fairytaleId);
        Collections.shuffle(randomFairytaleList);
        Fairytale recommendFairytale = randomFairytaleList.get(0);

        return new FairytaleDetailResponseDto(description, recommendFairytale.getRecommendImageUrl());
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void FairytalePlay() {
        userFairytaleRepository.findAll().forEach(userFairytale -> {
            userFairytale.setFairytalePlay(false);
            userFairytaleRepository.save(userFairytale);
        });
    }

}
