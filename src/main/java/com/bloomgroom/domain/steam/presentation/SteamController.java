package com.bloomgroom.domain.steam.presentation;

import com.bloomgroom.domain.steam.application.SteamService;

import com.bloomgroom.global.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/steam")
@RequiredArgsConstructor
@Tag(name = "Steam", description = "Steam API")

public class SteamController {

    private final SteamService steamService;


    // 수증기 생성
    @PostMapping("/{bigGoalId}")
    @Operation(summary = "수증기 생성", description = "수증기 생성 API")
    public ResponseEntity<ApiResponse> createSteam(@PathVariable Long bigGoalId) {
        return steamService.createSteam(bigGoalId);
    }

    // 수증기 리스트 조회
    @GetMapping("/list/{bigGoalId}")
    @Operation(summary = "수증기 리스트 조회", description = "수증기 리스트 조회 API")
    public ResponseEntity<ApiResponse> getSteamList(@PathVariable Long bigGoalId) {
        return steamService.getSteamList(bigGoalId);

    }
}
