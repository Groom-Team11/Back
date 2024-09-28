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
public class NewFlowerRes {
    @Schema(type = "flower_id", example = "1", description = "꽃 id")
    private Long flowerId;

    @Schema(type = "String", example = "https://bloomgroom-bucket.s3.ap-northeast-2.amazonaws.com/flower_image/sunflower.png", description = "꽃 이미지 url")
    private String flowerImage;

}
