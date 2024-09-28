package com.bloomgroom.domain.biggoal.dto.response;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.domain.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BigGoalRes {
    private Long bigGoalId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Priority priority;
    private String content;
    private Boolean goalStatus;
    private int achievementRate;

}
