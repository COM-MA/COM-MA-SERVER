package com.example.comma.domain.external.controller;

import com.example.comma.domain.external.service.GeminiService;
import com.example.comma.domain.external.service.ImageCrawler;
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
    private final GeminiService geminiService;
    @GetMapping("/gemini")
    public ResponseEntity<SuccessResponse<?>> generateResponse(@RequestParam(name = "text") String text) {
        String response = geminiService.generateResponse(text);
        return SuccessResponse.ok(response);
    }

}
