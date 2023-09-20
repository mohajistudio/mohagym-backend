package io.mohajistudio.mohagym.interceptor;

import io.mohajistudio.mohagym.configuration.exception.CustomException;
import io.mohajistudio.mohagym.configuration.exception.ErrorCode;
import io.mohajistudio.mohagym.core.security.role.Role;
import io.mohajistudio.mohagym.provider.security.JwtAuthToken;
import io.mohajistudio.mohagym.provider.security.JwtAuthTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
//admin만 사용 가능한 요청의 인터셉터
//config로 로그인이 되었는지,토큰이 유효한지 를 확인하는 인터셉터로 먼저 수행
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    //요청이 처리되기 전에 실행되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());


        /* 요청을 보낸 유저 역할 가져오기 */
        String role = jwtAuthToken.roleFromClaim();
        /* 유저의 역할이 admin이어야 접근 가능 */
        if (role.equals(Role.ADMIN.getCode())) {
            return true;
        }throw new CustomException(ErrorCode.NOT_AUTHORIZED);



    }
}
