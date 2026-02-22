package com.lhj.myoppingmall.auth.repository;

import com.lhj.myoppingmall.auth.entity.AuthToken;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from AuthToken a where a.id = :id")
    Optional<AuthToken> findByIdForUpdate(@Param("id") Long id);

    Optional<AuthToken> findByRefreshToken(String refreshToken);
}
