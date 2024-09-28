package com.bloomgroom.domain.biggoal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BigGoalStatusScheduler {

    private final BigGoalService bigGoalService;

    @Scheduled(cron = "0 0 0 * * ?") // 초 분 시 일 월 요일 -> 매일 정각에 실행하도록
    public void updateGoalStatus() {
        bigGoalService.updateGoalStatus(); 
    }
}
