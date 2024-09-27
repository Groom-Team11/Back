package com.bloomgroom.domain.smallgoal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSmallGoalReq {
    private Long bigGoalId;  // 장기 목표 ID
    private String content = "";   // 세부 목표 내용 (기본값: 빈 문자열)
    private Boolean goalStatus = false; // 목표 상태 (기본값: false)
}
