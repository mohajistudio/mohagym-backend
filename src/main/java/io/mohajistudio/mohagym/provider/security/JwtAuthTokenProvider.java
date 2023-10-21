package io.mohajistudio.mohagym.provider.security;


import io.jsonwebtoken.security.Keys;
import io.mohajistudio.mohagym.core.security.AuthTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
@Slf4j
public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {
    //HTTP 요청 헤더에 포함되는 헤더 필드의 이름
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final Key key;

    public JwtAuthTokenProvider(String secret){

        this.key = Keys.hmacShaKeyFor( //HMAC-SHA 알고리즘을 사용하여 해당 바이트 배열로부터 키를 생성 //메시지 무결성을 보호하기 위한 해시 기반 메시지 인증 코드를 생성하는 데 사용되며, JWT의 서명 검증에 사용
                secret.getBytes());//secret 문자열을 바이트 배열로 변환
    }

    //새로운 JwtAuthToken 인스턴스 생성// 새로운 토큰 생성
    @Override
    public JwtAuthToken createAuthToken(String id, String role, Date expiredDate) {
        return new JwtAuthToken(id, role, expiredDate, key);
    }

    //토큰 문자열을 가지고 JwtAuthToken 인스턴스 생성
    @Override
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }

    public String getEmailFromToken(String token){
        JwtAuthToken jwtAuthToken = convertAuthToken(token);
        return jwtAuthToken.EmailFromClaim();
    }

    @Override
    public Optional<String> getAuthToken(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION_HEADER);    //헤더에서 토큰 꺼내기
        if(StringUtils.hasText(authToken)){ //authToken 문자열이 비어있지 않은지 확인//StringUtils.hasText()는 null이 아니고 비어 있지 않으면 true를 반환하고, 그렇지 않으면 false를 반환
            return Optional.of(authToken);  //문자열이 비어있지 않으면 문자열인 authToken을 Optional로 반환
        }else {
            return Optional.empty();//문자열이 비어있거나 헤더에서 토큰이 발견되지 않은 경우 Optional의 빈 값 반환
        }
    }
}
