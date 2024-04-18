package com.example.comma.domain.external.service.controller;

import com.example.comma.domain.external.service.service.ImageCrawler;
import com.example.comma.global.common.SuccessResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@RestController
public class GeminniController {

    private final ImageCrawler imageCrawler;
    private static final String API_ENDPOINT_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private static final String API_KEY = "AIzaSyCgt_fuEZ2fU4z_t1KoHaNIxur_ML7kjgY";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/geminni")
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
            e.printStackTrace(); // 오류 발생 시 로그에 출력
        }
        return null; // 이미지가 발견되지 않았을 경우 null 반환
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
