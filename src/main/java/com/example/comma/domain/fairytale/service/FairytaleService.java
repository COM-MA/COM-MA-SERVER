package com.example.comma.domain.fairytale.service;

import com.example.comma.domain.fairytale.dto.FairytaleDetailResponseDto;
import com.example.comma.domain.fairytale.dto.FairytaleResponseDto;
import com.example.comma.domain.fairytale.entity.Fairytale;
import com.example.comma.domain.fairytale.repository.FairytaleRepository;
import com.example.comma.global.error.exception.EntityNotFoundException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.comma.global.error.ErrorCode.FAIRYTALE_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class FairytaleService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final FairytaleRepository fairytaleRepository;


    public String searchVideo(String title, String channelName) throws IOException {
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
            String videoTitle = searchResult.getSnippet().getTitle();

            return "https://www.youtube.com/watch?v=" + videoId;
        }

        return "검색 결과가 없습니다";
    }

    public List<FairytaleResponseDto> getFairytale() {
        List<Fairytale> fairytaleList = fairytaleRepository.findAll();

        return fairytaleList.stream()
                .map(fairytale -> {
                    try {
                        String videolink = searchVideo(fairytale.getTitle(), fairytale.getChannelName());


                        return new FairytaleResponseDto(
                                fairytale.getId(),
                                fairytale.getTitle(),
                                fairytale.getImgaeUrl(),
                                fairytale.getChannelName(),
                                fairytale.getYear().toString(),
                                fairytale.getTime().toString(),
                                (videolink != null) ? videolink : fairytale.getLink(),
                                fairytale.getSubtitleTag(),
                                fairytale.getSignTag()
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    public FairytaleDetailResponseDto getFairytaleDetail(Long fairytaleId) {
        Fairytale fairytale = fairytaleRepository.findById(fairytaleId)
                .orElseThrow(() -> new EntityNotFoundException(FAIRYTALE_NOT_FOUND)
        );

        String description = fairytale.getDescription();

        List<Fairytale> randomFairytaleList = fairytaleRepository.findByIdNot(fairytaleId);
        Collections.shuffle(randomFairytaleList);
        Fairytale recommendFairytale = randomFairytaleList.get(0);

        return new FairytaleDetailResponseDto(description, recommendFairytale.getRecommendImageUrl());
    }

}
