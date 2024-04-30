package com.example.comma.domain.external.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GeminiService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateResponse(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        text ="한국 수화 단어 중 " + text + "에 대한 수형 설명해줘";
        String requestBody = "{\"contents\": [{\"parts\":[{\"text\":\"" + text + "\"}]}]}";

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
            String textResponse = extractTextFromResponse(responseBody);
            return textResponse;
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