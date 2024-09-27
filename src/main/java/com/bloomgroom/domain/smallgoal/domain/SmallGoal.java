package com.bloomgroom.domain.smallgoal.domain;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmallGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    private Long smallGoalId;

    @ManyToOne
    @JoinColumn(name = "bigGoal_id", nullable = false)
    private BigGoal bigGoal;

    private String content;

    private Boolean goalStatus;

    private LocalDateTime smallGoalDate;

    @PrePersist
    protected void onCreate() {
        this.smallGoalDate = LocalDateTime.now(); // 현재 날짜와 시간으로 설정
    }
}
