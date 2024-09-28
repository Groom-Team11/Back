package com.bloomgroom.domain.steam.dto.response;

import com.bloomgroom.domain.steam.domain.Steam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SteamRes {
    private Long steamId; // 수증기 ID
    private Long bigGoalId; // 연관된 장기목표 ID
    private LocalDateTime steamDate; // 수증기 생성 날짜

    // Entity to DTO 변환 메서드
    public SteamRes(Steam steam) {
        this.steamId = steam.getSteamId();
        this.bigGoalId = steam.getBigGoal().getBigGoalId();
        this.steamDate = steam.getSteamDate();
    }
}
