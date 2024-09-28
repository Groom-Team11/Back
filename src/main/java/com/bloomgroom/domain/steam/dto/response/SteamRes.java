package com.bloomgroom.domain.steam.dto.response;

import com.bloomgroom.domain.steam.domain.Steam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SteamRes {

    @Schema(description = "수증기 ID")
    private Long steamId;

    @Schema(description = "장기 목표 ID")
    private Long bigGoalId;

    @Schema(description = "수증기 생성 날짜")
    private LocalDateTime steamDate;

    public SteamRes(Steam steam) {
        this.steamId = steam.getSteamId();
        this.bigGoalId = steam.getBigGoal().getBigGoalId(); // 연관된 장기 목표 ID 설정
        this.steamDate = steam.getSteamDate();
    }
}
