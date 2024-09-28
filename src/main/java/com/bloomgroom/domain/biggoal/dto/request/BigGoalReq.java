package com.bloomgroom.domain.biggoal.dto.request;


import com.bloomgroom.domain.biggoal.domain.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BigGoalReq {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Priority priority;
    private String content;

}
