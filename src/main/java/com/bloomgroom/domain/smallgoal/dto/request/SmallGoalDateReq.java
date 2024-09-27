package com.bloomgroom.domain.smallgoal.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmallGoalDateReq {
    private LocalDateTime smallGoalDate;
}
