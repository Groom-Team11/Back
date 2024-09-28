package com.bloomgroom.domain.steam.domain;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.smallgoal.domain.SmallGoal;
import jakarta.persistence.*;
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
public class Steam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long steamId;

    @ManyToOne
    @JoinColumn(name = "bigGoal_id", nullable = false)
    private BigGoal bigGoal; // 연관된 장기목표 (다대일 관계)

    @Column(nullable = false)
    private LocalDateTime steamDate; // 수증기 생성 날짜

}
