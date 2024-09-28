package com.bloomgroom.domain.smallgoal.presentation;

import com.bloomgroom.domain.smallgoal.application.SmallGoalService;
import com.bloomgroom.domain.smallgoal.dto.request.CreateSmallGoalReq;
import com.bloomgroom.domain.smallgoal.dto.request.SmallGoalDateReq;
import com.bloomgroom.domain.smallgoal.dto.request.UpdateSmallGoalReq;
import com.bloomgroom.global.payload.ApiResponse;
<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/small-goal")
@RequiredArgsConstructor
<<<<<<< HEAD
@Tag(name = "Small-Goal", description = "Small-Goal API")
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
public class SmallGoalController {

    private final SmallGoalService smallGoalService;

    // 생성
    @PostMapping("/{bigGoalId}")
<<<<<<< HEAD
    @Operation(summary = "세부목표 생성", description = "세부목표 생성 API")
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
    public ResponseEntity<ApiResponse> createSmallGoal(@PathVariable Long bigGoalId, @RequestBody CreateSmallGoalReq request) {
        request.setBigGoalId(bigGoalId); // 요청 객체에 장기 목표 ID 설정
        return smallGoalService.createSmallGoal(request);
    }

    // 조회
    @GetMapping("/list/{bigGoalId}")
<<<<<<< HEAD
    @Operation(summary = "세부목표 조회", description = "세부목표 조회 API")
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
    public ResponseEntity<ApiResponse> getSmallGoalsByBigGoalId(@PathVariable Long bigGoalId) {
        return smallGoalService.getSmallGoalsByBigGoalId(bigGoalId);
    }

    // 수정
    @PatchMapping("/{smallGoalId}")
<<<<<<< HEAD
    @Operation(summary = "세부목표 수정", description = "세부목표 수정 API")
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
    public ResponseEntity<ApiResponse> updateSmallGoal(@PathVariable Long smallGoalId, @RequestBody UpdateSmallGoalReq request) {
        return smallGoalService.updateSmallGoal(smallGoalId, request);
    }

    // 삭제
    @DeleteMapping("/{smallGoalId}")
<<<<<<< HEAD
    @Operation(summary = "세부목표 삭제", description = "세부목표 삭제 API")
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
    public ResponseEntity<ApiResponse> deleteSmallGoal(@PathVariable Long smallGoalId) {
        return smallGoalService.deleteSmallGoal(smallGoalId);
    }

    // 날짜로 조회
    @PostMapping("/date-list/{bigGoalId}")
<<<<<<< HEAD
    @Operation(summary = "날짜별 세부목표 조회", description = "날짜별 세부목표 조회 API")
=======
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
    public ResponseEntity<ApiResponse> getSmallGoalsByDate(@PathVariable Long bigGoalId, @RequestBody SmallGoalDateReq request) {
        return smallGoalService.getSmallGoalsByDate(bigGoalId, request.getSmallGoalDate());
    }
}