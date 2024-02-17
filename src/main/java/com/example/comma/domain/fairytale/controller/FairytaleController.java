package com.example.comma.domain.fairytale.controller;

import com.example.comma.domain.fairytale.dto.FairytaleDetailResponseDto;
import com.example.comma.domain.fairytale.dto.FairytaleResponseDto;
import com.example.comma.domain.fairytale.service.FairytaleService;
import com.example.comma.global.common.SuccessResponse;
import com.example.comma.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/detail/{fairytaleId}")
    public ResponseEntity<SuccessResponse<?>> getFairytaleDetail(@UserId Long userId,@PathVariable(name = "fairytaleId") Long fairytaleId) {
        FairytaleDetailResponseDto fairytale = fairytaleService.getFairytaleDetail(userId,fairytaleId);
        return SuccessResponse.ok(fairytale);
    }
}
