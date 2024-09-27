package com.bloomgroom.domain.biggoal.dto.request;

import com.bloomgroom.domain.biggoal.domain.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BigGoalUpdateReq {

    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Priority priority;
}
