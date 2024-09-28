package com.bloomgroom.global.payload;

import com.bloomgroom.domain.smallgoal.domain.SmallGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Data
public class ApiResponse {

    @Schema(type = "boolean", example = "true", description = "올바르게 로직을 처리했으면 True, 아니면 False를 반환합니다.")
    private boolean check;

    @Schema(type = "object", example = "information", description = "restful의 정보를 감싸 표현합니다. object형식으로 표현합니다.")
    private Object information;

    public ApiResponse() {}

    @Builder
    public ApiResponse(boolean check, Object information) {
        this.check = check;
        this.information = information;
    }

    public static ApiResponse toApiResponse(Object response) {
        return ApiResponse.builder()
                .check(true)
                .information(response)
                .build();
    }

    // 새로운 메소드 추가: 세부 목표 목록과 개수를 포함한 ApiResponse 생성
    public static ApiResponse toApiResponseWithCount(List<SmallGoal> smallGoals) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("smallGoals", smallGoals);
        responseData.put("count", smallGoals.size());

        return ApiResponse.builder()
                .check(true)
                .information(responseData)
                .build();
    }
}
