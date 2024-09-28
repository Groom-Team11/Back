package com.bloomgroom.domain.biggoal.presentation;

import com.bloomgroom.domain.biggoal.application.BigGoalService;
import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalReq;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalUpdateReq;
import com.bloomgroom.domain.biggoal.dto.response.BigGoalRes;
import com.bloomgroom.domain.user.security.CustomUserDetails;
import com.bloomgroom.global.payload.ApiResponse;
import com.bloomgroom.global.payload.ErrorCode;
import com.bloomgroom.global.payload.ErrorResponse;
import com.bloomgroom.global.payload.exception.BusinessBaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/big-goal")
@Tag(name = "Big-Goal", description = "Big-Goal API")
public class BigGoalController {

    private final BigGoalService bigGoalService;

    //1. 장기목표 생성
    @PostMapping
    @Operation(summary = "장기목표 생성", description = "장기목표 생성 API")
    public ResponseEntity<ApiResponse> createBigGoal(@RequestBody BigGoalReq bigGoalReq,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        try {
            // 인증된 유저 확인하기
            if (userDetails == null || userDetails.getUsername() == null) {
                throw new BusinessBaseException(ErrorCode.UNAUTHORIZED_USER);
            }

            Long userId = Long.parseLong(userDetails.getUsername()); //인증된 유저만이 장기목표 생성할 수 있도록하기 위해서
            BigGoal createdBigGoal = bigGoalService.createBigGoal(bigGoalReq, userId);

            return ResponseEntity.ok(ApiResponse.toApiResponse(createdBigGoal));
        } catch (BusinessBaseException e) {
            return ResponseEntity.badRequest().body(ApiResponse.toApiResponse(ErrorResponse.of(e.getErrorCode())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.toApiResponse(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    //2. 달성된 장기 목표 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBigGoal(@PathVariable Long id,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            // 인증된 유저 확인
            if (userDetails == null || userDetails.getUser() == null) {
                throw new BusinessBaseException(ErrorCode.UNAUTHORIZED_USER);
            }

            Long userId = userDetails.getUser().getUserId();

            bigGoalService.deleteBigGoal(id, userId);

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
                                                      @RequestBody BigGoalUpdateReq bigGoalUpdateReq,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            // 인증된 유저 확인
            if (userDetails == null || userDetails.getUser() == null) {
                throw new BusinessBaseException(ErrorCode.UNAUTHORIZED_USER);
            }

            Long userId = userDetails.getUser().getUserId();

            BigGoal updatedBigGoal = bigGoalService.updateBigGoal(id, bigGoalUpdateReq, userId);
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
    public ResponseEntity<ApiResponse> getBigGoalList(@AuthenticationPrincipal CustomUserDetails userDetails) {

        // 인증된 유저 확인
        // 인증된 유저만이 리스트도 조회 가능하도록
        if (userDetails == null || userDetails.getUsername() == null) {
            throw new BusinessBaseException(ErrorCode.UNAUTHORIZED_USER);
        }

        try {
            List<BigGoal> bigGoals = bigGoalService.getBigGoalsSorted();

            // 응답에 대한 DTO 변환 로직 추가
            List<BigGoalRes> responses = bigGoals.stream()
                    .map(bigGoal -> {
                        BigGoalRes response = new BigGoalRes();
                        response.setBigGoalId(bigGoal.getBigGoalId());
                        response.setUserId(bigGoal.getUser().getUserId());
                        response.setStartDate(bigGoal.getStartDate());
                        response.setEndDate(bigGoal.getEndDate());
                        response.setPriority(bigGoal.getPriority());
                        response.setContent(bigGoal.getContent());
                        response.setGoalStatus(bigGoal.getGoalStatus());

                        // achievementRate 계산
                        int achievementRate = bigGoalService.calculateAchievementRate(bigGoal);
                        response.setAchievementRate(achievementRate);

                        return response;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.toApiResponse(responses));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.toApiResponse(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
        
        // 주석
    }



}
