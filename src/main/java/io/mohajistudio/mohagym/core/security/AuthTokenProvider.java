package io.mohajistudio.mohagym.core.security;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Optional;

public interface AuthTokenProvider<T> {
    //인증 토큰 생성
    T createAuthToken(String id, String role, Date expiredDate);

    //문자열 토큰을 객체로 변환하는 메서드
    T convertAuthToken(String token);

    //HTTP 요청에서 인증 토큰을 추출하는 메서드
    Optional<String> getAuthToken(HttpServletRequest request);
}
