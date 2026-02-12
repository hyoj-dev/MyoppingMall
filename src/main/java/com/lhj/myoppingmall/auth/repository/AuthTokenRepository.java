package com.lhj.myoppingmall.auth.repository;

import com.lhj.myoppingmall.auth.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

}
