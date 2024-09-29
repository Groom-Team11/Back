package com.bloomgroom.domain.smallgoal.application;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.domain.repository.BigGoalRepository;
import com.bloomgroom.domain.smallgoal.domain.SmallGoal;
import com.bloomgroom.domain.smallgoal.domain.repository.SmallGoalRepository;
import com.bloomgroom.domain.smallgoal.dto.request.CreateSmallGoalReq;
import com.bloomgroom.domain.smallgoal.dto.request.UpdateSmallGoalReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.bloomgroom.global.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class SmallGoalService {

    private final SmallGoalRepository smallGoalRepository;
    private final BigGoalRepository bigGoalRepository;

    // 세부 목표 생성
    public ResponseEntity<ApiResponse> createSmallGoal(CreateSmallGoalReq request) {
        SmallGoal smallGoal = new SmallGoal();

        BigGoal bigGoal = bigGoalRepository.findById(request.getBigGoalId())
                .orElseThrow(() -> new RuntimeException("장기 목표를 찾을 수 없습니다."));
        smallGoal.setBigGoal(bigGoal);
        smallGoal.setContent(request.getContent());
        smallGoal.setGoalStatus(request.getGoalStatus());
        smallGoal = smallGoalRepository.save(smallGoal);

        ApiResponse response = ApiResponse.toApiResponse(smallGoal);
        return ResponseEntity.ok(response);
    }

    // 특정 장기 목표의 세부 목표 조회
    public ResponseEntity<ApiResponse> getSmallGoalsByBigGoalId(Long bigGoalId) {
        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId)
                .orElseThrow(() -> new RuntimeException("장기 목표를 찾을 수 없습니다."));

        List<SmallGoal> smallGoals = smallGoalRepository.findByBigGoal(bigGoal);
        ApiResponse response = ApiResponse.toApiResponse(smallGoals);

        return ResponseEntity.ok(response);
    }

    // 세부 목표 수정
    @Transactional
    public ResponseEntity<ApiResponse> updateSmallGoal(Long smallGoalId, UpdateSmallGoalReq request) {
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId)
                .orElseThrow(() -> new RuntimeException("세부 목표를 찾을 수 없습니다."));


        // goalStatus만 업데이트
        if (request.getGoalStatus() != null) {
            smallGoal.updateGoalStatus(request.getGoalStatus());
            System.out.println("Updated goalStatus = " + smallGoal.getGoalStatus());
        }

        smallGoal = smallGoalRepository.save(smallGoal);

        ApiResponse response = ApiResponse.toApiResponse(smallGoal);

        return ResponseEntity.ok(response);
    }



    // 세부 목표 삭제
    public ResponseEntity<ApiResponse> deleteSmallGoal(Long smallGoalId) {
        smallGoalRepository.deleteById(smallGoalId);
        ApiResponse response = ApiResponse.toApiResponse("세부 목표가 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }

    // 날짜로 세부 목표 조회
    public ResponseEntity<ApiResponse> getSmallGoalsByDate(Long bigGoalId, LocalDateTime smallGoalDate) {

        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId)
                .orElseThrow(() -> new RuntimeException("장기 목표를 찾을 수 없습니다."));

        List<SmallGoal> smallGoals = smallGoalRepository.findByBigGoal(bigGoal).stream()
                .filter(goal -> goal.getSmallGoalDate().toLocalDate().isEqual(smallGoalDate.toLocalDate())) // 날짜 비교
                .collect(Collectors.toList());

        // 개수를 포함한 ApiResponse 생성
        ApiResponse response = ApiResponse.toApiResponseWithCount(smallGoals);

        return ResponseEntity.ok(response);
    }
}
