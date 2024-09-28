package com.bloomgroom.domain.biggoal.domain;

import com.bloomgroom.domain.smallgoal.domain.SmallGoal;
import com.bloomgroom.domain.steam.domain.Steam;
import com.bloomgroom.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BigGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bigGoal_id")
    private Long bigGoalId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotNull
    private String content; //장기목표 내용

    @NotNull
    private Boolean goalStatus; //장기목표 달성 여부

    @NotNull
    private Long cnt;

    @OneToMany(mappedBy = "bigGoal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SmallGoal> smallGoals = new ArrayList<>(); // 양방향 관계를 위한 필드

    @OneToMany(mappedBy = "bigGoal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Steam> steams = new ArrayList<>(); // 수증기 리스트

}
