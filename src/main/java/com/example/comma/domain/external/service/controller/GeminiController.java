package com.example.comma.domain.external.service.controller;

import com.example.comma.domain.external.service.service.ImageCrawler;
import com.example.comma.global.common.SuccessResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@RequiredArgsConstructor
@RestController
public class GeminiController {

    private final ImageCrawler imageCrawler;
    private static final String API_ENDPOINT_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";

    @Value("${gemini.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/gemini")
    public ResponseEntity<?> generateImage(@RequestParam(name = "text") String text) {
        String requestBody = "{\"contents\": [{\"parts\": [{\"text\": \"" + text + "\"}]}]}";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_ENDPOINT_URL)
                .queryParam("key", API_KEY);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(builder.toUriString(), requestBody, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            String imageBase64 = extractImage(responseBody);

            if (!StringUtils.isEmpty(imageBase64)) {
                byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
            }
        }

        return ResponseEntity.notFound().build();
    }

    private String extractImage(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode candidatesNode = rootNode.get("candidates");

            if (candidatesNode.isArray()) {
                for (JsonNode candidateNode : candidatesNode) {
                    JsonNode contentNode = candidateNode.get("content");
                    JsonNode partsNode = contentNode.get("parts");

                    if (partsNode.isArray()) {
                        for (JsonNode partNode : partsNode) {
                            String partText = partNode.get("text").asText();
                            if (isImage(partText)) {
                                return partText;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isImage(String text) {
        try {
            Base64.getDecoder().decode(text);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
