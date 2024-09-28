package com.bloomgroom.domain.flower.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FlowerListRes {
    @Schema(type = "List", description = "꽃 리스트")
    private List<FlowerDetailRes> flowerList;
}
