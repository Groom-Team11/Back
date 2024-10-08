package com.bloomgroom.domain.user.domain.repository;

import com.bloomgroom.domain.user.domain.RefreshToken;
import com.bloomgroom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    RefreshToken findByUser(User user);
}
