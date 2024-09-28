package com.bloomgroom.domain.steam.presentation;

import com.bloomgroom.domain.steam.application.SteamService;
import com.bloomgroom.domain.steam.dto.response.SteamRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/steam")
@RequiredArgsConstructor
public class SteamController {

    private final SteamService steamService;

    // 수증기 생성 API - Request Body 없이 bigGoalId만 전달받음
    @PostMapping("/{bigGoalId}")
    public ResponseEntity<SteamRes> createSteam(@PathVariable Long bigGoalId) {
        SteamRes steamRes = steamService.createSteam(bigGoalId);
        return ResponseEntity.ok(steamRes);
    }

    // 수증기 리스트 조회 API
    @GetMapping("/list/{bigGoalId}")
    public ResponseEntity<List<SteamRes>> getSteamList(@PathVariable Long bigGoalId) {
        List<SteamRes> steamList = steamService.getSteamList(bigGoalId);
        return ResponseEntity.ok(steamList);
    }
}
