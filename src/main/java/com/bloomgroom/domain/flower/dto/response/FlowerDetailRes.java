package com.bloomgroom.domain.flower.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FlowerDetailRes {

    @Schema(type = "flower_id", example = "1", description = "꽃 id")
    private Long flowerId;

    @Schema(type = "String", example = "해바라기", description = "꽃 이름")
    private String flowerName;

    @Schema(type = "String", example = "https://bloomgroom-bucket.s3.ap-northeast-2.amazonaws.com/flower_image/sunflower.png", description = "꽃 이미지 url")
    private String flowerImage;

    @Schema(type = "String", example = "햇살을 받아 자라는 꽃", description = "꽃말")
    private String flowerMean;

    @Schema(type = "Boolean", example = "true", description = "꽃 획득 여부")
    private Boolean isAcquired;



}
