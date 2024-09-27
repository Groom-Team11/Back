package com.bloomgroom.domain.user.application;

import com.bloomgroom.domain.user.domain.RefreshToken;
import com.bloomgroom.domain.user.domain.User;
import com.bloomgroom.domain.user.domain.repository.RefreshTokenRepository;
import com.bloomgroom.domain.user.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    // AccessToken 생성
    public String createAccessToken(User user) { //AccessToken을 JWT형식으로 생성함
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) //1시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // RefreshToken 생성
    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 3600000)) //1주일
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // RefreshToken 저장
    public void saveRefreshToken(User user, String refreshToken) {

        RefreshToken token = new RefreshToken();

        token.setRefreshToken(refreshToken);
        token.setUser(user);
        refreshTokenRepository.save(token);
    }

    //RefreshToken 이용하여 AccessToken 재발급
    public String renewAccessToken(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).orElse(null);

        if (token == null) {
            return null;
        }
        User user = token.getUser();
        return createAccessToken(user);
    }

    // RefreshToken 조회
    public String getRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user);

        if (refreshToken != null) {
            return refreshToken.getRefreshToken();
        }
        return null;
    }

}
