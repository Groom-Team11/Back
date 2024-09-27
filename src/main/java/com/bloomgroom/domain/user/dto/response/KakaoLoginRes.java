package com.bloomgroom.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoLoginRes {

    private String accessToken;   // JWT 액세스 토큰
    private String refreshToken;  // 리프레시 토큰
}
