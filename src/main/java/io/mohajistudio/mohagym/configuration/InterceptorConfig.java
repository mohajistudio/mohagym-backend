package io.mohajistudio.mohagym.configuration;

/*import lombok.RequiredArgsConstructor;
import mohajistudio.mohagym.foradministrators.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;*/

import io.mohajistudio.mohagym.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

//AuthInterceptor의 config 클래스// 일단 깊게 공부x
@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePatterns = Arrays.asList("/h2-console/**","/register","/login");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")// 전체 경로에 대해서 인터셉터 등록
                .excludePathPatterns(excludePatterns); //해당 경로 인터셉터 예외 설정
    }

}
