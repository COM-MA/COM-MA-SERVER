package com.example.comma.domain.external.service;

import com.example.comma.domain.card.dto.response.DescriptionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GeminiService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String apiKey;

    //수형 설명 검색

    public String generateSignDescription(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        text = "한국 수화 단어 중 " + text + "에 대한 수화 동작 방법만 작성";
        String requestBody = "{\"contents\": [{\"parts\":[{\"text\":\"" + text + "\"}]}]}";

        return sendGeminiResponse(requestBody);
    }


    //단어 설명 검색
    public List<DescriptionResponseDto> generateDescriptionList(List<String> words) {
        List<DescriptionResponseDto> responses = new ArrayList<>();

        for (String text : words) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //단어 설명
            String requestBody1 = "{\"contents\": [{\"parts\":[{\"text\":\"1." + text + "단어에 대한 사전 정의 => 20자 이내로 답변은 하나씩만 작성\"}]}]}";
            // 품사
            String requestBody2 = "{\"contents\": [{\"parts\":[{\"text\":\"2." + text + "단어에 대한 품사 => 5자 이내로 답변은 하나씩만 작성\"}]}]}";

            String response1 = sendGeminiResponse(requestBody1);
            String response2 = sendGeminiResponse(requestBody2);

            responses.add(new DescriptionResponseDto(text, response1, response2));
        }

        return responses;
    }

    //Gemini API 호출
    private String sendGeminiResponse(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent")
                .queryParam("key", apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            return extractTextFromResponse(responseBody);
        } else {
            return "Failed to generate content. Status code: " + responseEntity.getStatusCodeValue();
        }
    }

    private String extractTextFromResponse(String responseBody) {
        int startIndex = responseBody.indexOf("\"text\":") + "\"text\":".length();
        int endIndex = responseBody.indexOf("\"role\"");
        String text = responseBody.substring(startIndex, endIndex);
        String cleanedText = text
                .replaceAll("[^가-힣a-zA-Z0-9.\\s]", "")
                .replaceAll("nn", "\n\n")
                .replaceAll("n", " ")
                .replaceAll("\\s+", " ");


        return cleanedText;
    }




}