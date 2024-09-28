package com.bloomgroom.domain.steam.application;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.domain.repository.BigGoalRepository;
import com.bloomgroom.domain.steam.domain.Steam;
import com.bloomgroom.domain.steam.domain.repository.SteamRepository;
import com.bloomgroom.domain.steam.dto.response.SteamRes;
import com.bloomgroom.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamService {

    private final SteamRepository steamRepository;
    private final BigGoalRepository bigGoalRepository;

    @Transactional
    public ResponseEntity<ApiResponse> createSteam(Long bigGoalId) {
        // BigGoal 엔티티 조회
        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 장기목표가 존재하지 않습니다: " + bigGoalId));

        // cnt 필드가 null인 경우 초기화
        if (bigGoal.getCnt() == null) {
            bigGoal.setCnt(0L);
        }

        // Steam 객체 생성 및 BigGoal의 steams 리스트에 추가
        Steam steam = new Steam();
        steam.setBigGoal(bigGoal); // 수증기에 연관된 장기목표 설정
        steam.setSteamDate(LocalDateTime.now());
        bigGoal.getSteams().add(steam);  // 양방향 관계 설정

        // 수증기 저장
        Steam savedSteam = steamRepository.save(steam);

        // 장기목표의 수증기 개수 증가
        bigGoal.setCnt(bigGoal.getCnt() + 1); // cnt가 null일 때 예외 방지
        bigGoalRepository.save(bigGoal);

        return ResponseEntity.ok(ApiResponse.toApiResponse(new SteamRes(savedSteam))); // 수증기 생성 결과 반환
    }

    public ResponseEntity<ApiResponse> getSteamList(Long bigGoalId) {
        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 장기목표가 존재하지 않습니다: " + bigGoalId));

        List<Steam> steamList = steamRepository.findByBigGoal(bigGoal);
        List<SteamRes> steamResponses = steamList.stream()
                .map(SteamRes::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.toApiResponse(steamResponses)); // 수증기 리스트 반환
    }
}
