package com.bloomgroom.domain.user.presentation;

import com.bloomgroom.domain.flower.application.FlowerDictionaryService;
import com.bloomgroom.domain.flower.domain.FlowerDictionary;
import com.bloomgroom.domain.flower.domain.repository.FlowerDictionaryRepository;
import com.bloomgroom.domain.user.application.TokenService;
import com.bloomgroom.domain.user.domain.User;
import com.bloomgroom.domain.user.application.UserService;
import com.bloomgroom.domain.user.dto.request.KakaoTokenReq;
import com.bloomgroom.domain.user.security.CustomUserDetails;
import com.bloomgroom.global.payload.ErrorCode;
import com.bloomgroom.global.payload.Message;
import com.bloomgroom.global.payload.exception.BusinessBaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.bloomgroom.global.payload.ApiResponse;


@Controller
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class UserController {


    private final UserService userService;
    private final TokenService tokenService;
    private final FlowerDictionaryService flowerDictionaryService;
    private final FlowerDictionaryRepository flowerDictionaryRepository;


    @GetMapping("/oauth2/callback/kakao")
    @Operation(summary = "회원가입 및 로그인", description = "유저의 정보가 있을 시 회원가입, 없을 시 로그인을 실시하는 API")
    public ResponseEntity<ApiResponse> kakaoLogin(@RequestParam(name = "code") String code) {

        // code로 accessToken 획득
        String accessToken = userService.getKakaoAccessToken(code);

        // accessToken으로 유저 정보 가져오기
        User kakaoUser = userService.getKakaoUser(accessToken);

        // 이메일로 유저 정보 조회
        User user = userService.findByEmail(kakaoUser.getEmail());

        if (user == null) {
            // 유저가 없으면 회원가입 처리
            user = userService.signup(kakaoUser);
            String refreshToken = tokenService.createRefreshToken(user);
            tokenService.saveRefreshToken(user, refreshToken); // DB에 RefreshToken 저장
            flowerDictionaryService.createDictionary(user); // 유저의 꽃 도감 생성

            // 성공 응답
            ApiResponse response = ApiResponse.builder()
                    .check(true)
                    .information(new Message("회원가입 성공"))
                    .build();
            return ResponseEntity.ok(response);

        } else {
            // 기존 사용자일 경우 로그인 처리
            String refreshToken = tokenService.getRefreshToken(user);

            if (refreshToken == null) {
                refreshToken = tokenService.createRefreshToken(user);
                tokenService.saveRefreshToken(user, refreshToken); // DB에 RefreshToken 저장
            }

            String newAccessToken = tokenService.renewAccessToken(refreshToken);

            // 사용자 인증 처리
            CustomUserDetails userDetails = new CustomUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String tokenInfo = String.format("Access Token: %s, Refresh Token: %s", newAccessToken, refreshToken);


            ApiResponse response = ApiResponse.builder()
                    .check(true)
                    .information(new Message("로그인 성공: " + tokenInfo))
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    // RefreshToken으로 AccessToken 재발급
    @PostMapping("/api/v1/user/refresh")
    public ResponseEntity<ApiResponse> refreshAccessToken(@RequestBody KakaoTokenReq kakaoTokenRequest) {
        String refreshToken = kakaoTokenRequest.getRefreshToken();
        String newAccessToken = tokenService.renewAccessToken(refreshToken);

        if (newAccessToken == null) {
            throw new BusinessBaseException("유효하지 않은 Refresh Token입니다.", ErrorCode.INVALID_TOKEN);
        }

        ApiResponse response = ApiResponse.builder()
                .check(true)
                .information(new Message("새로운 AccessToken: " + newAccessToken))
                .build();
        return ResponseEntity.ok(response);
    }
}
