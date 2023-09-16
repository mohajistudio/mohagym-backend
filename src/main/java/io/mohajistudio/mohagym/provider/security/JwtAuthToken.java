package io.mohajistudio.mohagym.provider.security;

import io.jsonwebtoken.*;
import io.mohajistudio.mohagym.configuration.exception.CustomException;
import io.mohajistudio.mohagym.configuration.exception.ErrorCode;
import io.mohajistudio.mohagym.core.security.AuthToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtAuthToken implements AuthToken<Claims> {

    //변경 불가능
    private final Key key;

    //변경 불가능
    @Getter
    private final String token;

    private static final String AUTHORITIES_KEY = "role";
    JwtAuthToken(String token, Key key) {
        this.key = key;
        this.token = token;
    }

    //key는 JwtAuthTokenProvider 클래스에서 생성자로 인스턴스와 함께 생성
    //expiredDate는 AdminService클래스에서 생성
    //role은 Role enum 에서 정의
    //id는 Member entity에서 생성
    //token은 해당 클래스인 JwtAuthToken 생성자로 인스턴스와 함께 생성
    JwtAuthToken(String id, String role, Date expiredDate, Key key) {
        this.key = key;
        this.token = Jwts.builder()
                .setSubject(id) //토큰 제목 -> 여기서는 어드민 아이디
                .claim(AUTHORITIES_KEY, role) //AUTHORITIES_KEY 상수와 role을 클레임에 추가
                .signWith(key, SignatureAlgorithm.HS256) //주어진 key와 서명 알고리즘을 사용하여 토큰에 서명
                .setExpiration(expiredDate)// 토큰의 만료 날짜 설정
                .compact(); //토큰을 문자열로 변환하고 반환
    }
    public String roleFromClaim(){
        Claims claims = getClaims();
        return  claims.get(AUTHORITIES_KEY,String.class);
    }

    public String userIdFromClaim(){
        Claims claims = getClaims();
        return claims.getSubject();
    }

    @Override
    public boolean validate() {
        return getClaims() != null; //클레임 정보를 호출하는 메서드를 실행하여 null 이면 false 존재하면 true반환
    }

    @Override
    public Claims getClaims() {
        /*return Jwts.parserBuilder()//jwt를 파싱하는 Builder 생성
                .setSigningKey(key)//주어진 key를 사용하여 JWT를 검증하기 위한 서명 키 설정//토큰을 서명할 때 사용한 키와 일치해야 함
                .build()//JwtParser를 빌드하여 JWT 파서를 생성
                .parseClaimsJws(token)//주어진 JWT 문자열(token)을 파싱하고, 서명이 유효한지 확인합니다.
                .getBody();//파싱된 JWT의 본문 부분을 추출하고 해당 클레임 정보를 Claims 객체로 반환합니다.
                *//*
                반환된 객체에서 다음과 같은 정보를 가져와 사용할 수 있다.
                Claims claims = getClaims();
                String userId = claims.getSubject(); // 사용자 ID
                String role =  claims.get("AUTHORITIES_KEY"); // 역할
                Date expirationDate = claims.getExpiration(); // 만료 날짜
                */
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }

    }
}
