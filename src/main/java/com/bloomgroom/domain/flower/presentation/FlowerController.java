package com.bloomgroom.domain.flower.presentation;

import com.bloomgroom.domain.flower.application.FlowerDictionaryService;
import com.bloomgroom.domain.flower.dto.response.FlowerListRes;
import com.bloomgroom.domain.flower.dto.response.NewFlowerRes;
import com.bloomgroom.domain.user.domain.User;
import com.bloomgroom.domain.user.security.CustomUserDetails;
import com.bloomgroom.global.payload.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/flower")
@Tag(name = "Flower", description = "꽃 관련 API")
public class FlowerController {

    private final FlowerDictionaryService flowerDictionaryService;

    @Operation(summary = "꽃 도감 조회 API", description = "사용자의 꽃 도감을 조회하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꽃 도감 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = FlowerListRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "꽃 도감 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/list")
    public ResponseEntity<?> getFlowerList(
            @Parameter @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        User user = userDetails.getUser();
        return flowerDictionaryService.getFlowerList(user);
    }

    @Operation(summary = "랜덤 꽃 획득 API", description = "랜덤으로 꽃을 획득하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 꽃 획득 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NewFlowerRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "랜덤 꽃 획득 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/new")
    public ResponseEntity<?> getRandomFlower(
            @Parameter @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        User user = userDetails.getUser();
        return flowerDictionaryService.getRandomFlower(user);
    }

}
