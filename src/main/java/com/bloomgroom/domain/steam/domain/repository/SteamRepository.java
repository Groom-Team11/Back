package com.bloomgroom.domain.steam.domain.repository;

import com.bloomgroom.domain.steam.domain.Steam;
import com.bloomgroom.domain.biggoal.domain.BigGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SteamRepository extends JpaRepository<Steam, Long> {
    // 특정 BigGoal에 연관된 수증기 리스트 조회
    List<Steam> findByBigGoal(BigGoal bigGoal);
}
