package com.bloomgroom.domain.smallgoal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSmallGoalReq {
    private Boolean goalStatus; // 수정할 목표 상태
}

