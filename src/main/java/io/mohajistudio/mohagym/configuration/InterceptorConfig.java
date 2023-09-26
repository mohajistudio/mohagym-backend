package io.mohajistudio.mohagym.configuration;


import io.mohajistudio.mohagym.interceptor.AuthenticationInterceptor;
import io.mohajistudio.mohagym.interceptor.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

//AuthInterceptor의 config 클래스
@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //인증//로그인한 사용자인지
    private final AuthenticationInterceptor authenticationInterceptor;
    //인가//역할이 맞는지
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePatterns = Arrays.asList("/h2-console/**","/favicon.ico","/register","/login","/token");
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")// 전체 경로에 대해서 인터셉터 등록
                .excludePathPatterns(excludePatterns)//해당 경로 인터셉터 예외 설정
                .order(1);

        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/admin/**")
                .order(2);
    }

}
