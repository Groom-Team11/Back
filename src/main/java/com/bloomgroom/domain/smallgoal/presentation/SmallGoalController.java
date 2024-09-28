package com.bloomgroom.domain.smallgoal.presentation;

import com.bloomgroom.domain.smallgoal.application.SmallGoalService;
import com.bloomgroom.domain.smallgoal.dto.request.CreateSmallGoalReq;
import com.bloomgroom.domain.smallgoal.dto.request.SmallGoalDateReq;
import com.bloomgroom.domain.smallgoal.dto.request.UpdateSmallGoalReq;
import com.bloomgroom.global.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/small-goal")
@RequiredArgsConstructor
@Tag(name = "Small-Goal", description = "Small-Goal API")
public class SmallGoalController {

    private final SmallGoalService smallGoalService;

    // 생성
    @PostMapping("/{bigGoalId}")
    @Operation(summary = "세부목표 생성", description = "세부목표 생성 API")
    public ResponseEntity<ApiResponse> createSmallGoal(@PathVariable Long bigGoalId, @RequestBody CreateSmallGoalReq request) {
        request.setBigGoalId(bigGoalId); // 요청 객체에 장기 목표 ID 설정
        return smallGoalService.createSmallGoal(request);
    }

    // 조회
    @GetMapping("/list/{bigGoalId}")
    @Operation(summary = "세부목표 조회", description = "세부목표 조회 API")
    public ResponseEntity<ApiResponse> getSmallGoalsByBigGoalId(@PathVariable Long bigGoalId) {
        return smallGoalService.getSmallGoalsByBigGoalId(bigGoalId);
    }

    // 수정
    @PatchMapping("/{smallGoalId}")
    @Operation(summary = "세부목표 수정", description = "세부목표 수정 API")
    public ResponseEntity<ApiResponse> updateSmallGoal(@PathVariable Long smallGoalId, @RequestBody UpdateSmallGoalReq request) {
        return smallGoalService.updateSmallGoal(smallGoalId, request);
    }

    // 삭제
    @DeleteMapping("/{smallGoalId}")
    @Operation(summary = "세부목표 삭제", description = "세부목표 삭제 API")
    public ResponseEntity<ApiResponse> deleteSmallGoal(@PathVariable Long smallGoalId) {
        return smallGoalService.deleteSmallGoal(smallGoalId);
    }

    // 날짜로 조회
    @PostMapping("/date-list/{bigGoalId}")
    @Operation(summary = "날짜별 세부목표 조회", description = "날짜별 세부목표 조회 API")
    public ResponseEntity<ApiResponse> getSmallGoalsByDate(@PathVariable Long bigGoalId, @RequestBody SmallGoalDateReq request) {
        return smallGoalService.getSmallGoalsByDate(bigGoalId, request.getSmallGoalDate());
    }
}