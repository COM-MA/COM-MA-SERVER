package com.example.comma.domain.fairytale.controller;

import com.example.comma.domain.fairytale.dto.FairytaleDetailResponseDto;
import com.example.comma.domain.fairytale.dto.FairytaleResponseDto;
import com.example.comma.domain.fairytale.service.FairytaleService;
import com.example.comma.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/fairytale")
@RestController
public class FairytaleController {
    private final FairytaleService fairytaleService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getFairytale() {
        List<FairytaleResponseDto> fairytale = fairytaleService.getFairytale();
        return SuccessResponse.ok(fairytale);
    }

    @GetMapping("/detail/{fairytaleId}")
    public ResponseEntity<SuccessResponse<?>> getFairytaleDetail(@PathVariable(name = "fairytaleId") Long fairytaleId) {
        FairytaleDetailResponseDto fairytale = fairytaleService.getFairytaleDetail(fairytaleId);
        return SuccessResponse.ok(fairytale);
    }
}
