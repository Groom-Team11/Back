package com.bloomgroom.domain.biggoal.presentation;

import com.bloomgroom.domain.biggoal.application.BigGoalService;
import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalReq;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalUpdateReq;
import com.bloomgroom.global.payload.ApiResponse;
import com.bloomgroom.global.payload.ErrorCode;
import com.bloomgroom.global.payload.ErrorResponse;
import com.bloomgroom.global.payload.exception.BusinessBaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/big-goal")
@Tag(name = "Big-Goal", description = "Big-Goal API")
public class BigGoalController {

    private final BigGoalService bigGoalService;

    //1. 장기목표 생성
    @PostMapping
    @Operation(summary = "장기목표 생성", description = "장기목표 생성 API")
    public ResponseEntity<ApiResponse> createBigGoal(@RequestBody BigGoalReq bigGoalReq) {
        try {
            BigGoal createdBigGoal = bigGoalService.createBigGoal(bigGoalReq);
            return ResponseEntity.ok(ApiResponse.toApiResponse(createdBigGoal));
        } catch (BusinessBaseException e) {
            return ResponseEntity.badRequest().body(ApiResponse.toApiResponse(ErrorResponse.of(e.getErrorCode())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.toApiResponse(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }
    
    //2. 달성된 장기 목표 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "장기목표 삭제", description = "장기목표 삭제 API")
    public ResponseEntity<ApiResponse> deleteBigGoal(@PathVariable Long id) {
        try {
            bigGoalService.deleteBigGoal(id);

            return ResponseEntity.ok(ApiResponse.toApiResponse("장기 목표가 성공적으로 삭제되었습니다."));
        } catch (BusinessBaseException e) {
            return ResponseEntity.badRequest().body(ApiResponse.toApiResponse(ErrorResponse.of(e.getErrorCode())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.toApiResponse(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    //3.장기 목표 수정
    @PatchMapping("/{id}")
    @Operation(summary = "장기목표 수정", description = "장기목표 수정 API")
    public ResponseEntity<ApiResponse> updateBigGoal( @PathVariable Long id,
                                                      @RequestBody BigGoalUpdateReq bigGoalUpdateReq) {
        try {
            BigGoal updatedBigGoal = bigGoalService.updateBigGoal(id, bigGoalUpdateReq);
            return ResponseEntity.ok(ApiResponse.toApiResponse(updatedBigGoal));
        } catch (BusinessBaseException e) {
            return ResponseEntity.badRequest().body(ApiResponse.toApiResponse(ErrorResponse.of(e.getErrorCode())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.toApiResponse(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    //4.장기목표 리스트 (우선순위 순으로)
    @GetMapping("/list")
    @Operation(summary = "장기목표 리스트", description = "장기목표를 우선 순위에 따라 정렬하여 반환하는 API")
    public ResponseEntity<ApiResponse> getBigGoalList() {
        try {
            List<BigGoal> bigGoals = bigGoalService.getBigGoalsSorted();
            return ResponseEntity.ok(ApiResponse.toApiResponse(bigGoals));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.toApiResponse(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }


}
