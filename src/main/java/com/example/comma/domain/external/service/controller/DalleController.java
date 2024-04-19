package com.example.comma.domain.external.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class DalleController {

    @Value("${dalle.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/dalle")
    public ResponseEntity<byte[]> generateImage(@RequestParam("text") String text) {
        // DALL-E API 엔드포인트
        String apiUrl = "https://api.openai.com/v1/davinci/generate";

        // API 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // API 요청 바디 생성
        String requestBody = "{\"prompt\": \"" + text + "\", \"max_tokens\": 50}";

        // API 요청 보내기
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, byte[].class);

        // API 응답 반환
        return ResponseEntity.status(responseEntity.getStatusCode()).contentType(MediaType.IMAGE_JPEG).body(responseEntity.getBody());
    }
}
