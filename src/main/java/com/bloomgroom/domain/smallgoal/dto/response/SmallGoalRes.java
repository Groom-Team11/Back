package com.bloomgroom.domain.smallgoal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SmallGoalRes {
    private Long smallGoalId;
    private Long bigGoalId;
    private String content;
    private LocalDateTime smallGoalDate;
    private Boolean goalStatus;
}