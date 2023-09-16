package io.mohajistudio.mohagym.interceptor;


import io.mohajistudio.mohagym.configuration.exception.CustomException;
import io.mohajistudio.mohagym.configuration.exception.ErrorCode;
import io.mohajistudio.mohagym.provider.security.JwtAuthToken;
import io.mohajistudio.mohagym.provider.security.JwtAuthTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    //요청이 처리되기 전에 실행되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        if(token.isPresent()){ //토큰이 있다면
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            if(jwtAuthToken.validate()){ //유효하면
                return true;
            }
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }
        throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }
}

