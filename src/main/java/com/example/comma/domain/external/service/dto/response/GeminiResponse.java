package com.example.comma.domain.external.service.dto.response;

import lombok.Data;

@Data
public class GeminiResponse {
    private boolean success;
    private byte[] imageData;
}
