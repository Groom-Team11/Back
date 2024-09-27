package com.bloomgroom.domain.smallgoal.presentation;

import com.bloomgroom.domain.smallgoal.application.SmallGoalService;
import com.bloomgroom.domain.smallgoal.dto.request.CreateSmallGoalReq;
import com.bloomgroom.domain.smallgoal.dto.request.SmallGoalDateReq;
import com.bloomgroom.domain.smallgoal.dto.request.UpdateSmallGoalReq;
import com.bloomgroom.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/small-goal")
@RequiredArgsConstructor
public class SmallGoalController {

    private final SmallGoalService smallGoalService;

    // 생성
    @PostMapping("/{bigGoalId}")
    public ResponseEntity<ApiResponse> createSmallGoal(@PathVariable Long bigGoalId, @RequestBody CreateSmallGoalReq request) {
        request.setBigGoalId(bigGoalId); // 요청 객체에 장기 목표 ID 설정
        return smallGoalService.createSmallGoal(request);
    }

    // 조회
    @GetMapping("/list/{bigGoalId}")
    public ResponseEntity<ApiResponse> getSmallGoalsByBigGoalId(@PathVariable Long bigGoalId) {
        return smallGoalService.getSmallGoalsByBigGoalId(bigGoalId);
    }

    // 수정
    @PatchMapping("/{smallGoalId}")
    public ResponseEntity<ApiResponse> updateSmallGoal(@PathVariable Long smallGoalId, @RequestBody UpdateSmallGoalReq request) {
        return smallGoalService.updateSmallGoal(smallGoalId, request);
    }

    // 삭제
    @DeleteMapping("/{smallGoalId}")
    public ResponseEntity<ApiResponse> deleteSmallGoal(@PathVariable Long smallGoalId) {
        return smallGoalService.deleteSmallGoal(smallGoalId);
    }

    // 날짜로 조회
    @PostMapping("/list/{bigGoalId}")
    public ResponseEntity<ApiResponse> getSmallGoalsByDate(@PathVariable Long bigGoalId, @RequestBody SmallGoalDateReq request) {
        return smallGoalService.getSmallGoalsByDate(bigGoalId, request.getSmallGoalDate());
    }
}