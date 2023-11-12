package io.mohajistudio.mohagym.configuration;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
public class WebConfig  {


    //AuthInterceptor로 권한처리 대체
    /*//예외처리
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.//토큰방식
                .csrf((csrf) -> csrf.disable())

                // 특정 URL에 대한 권한 설정.
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/user/**").authenticated();

                    authorizeRequests.requestMatchers("/admin/**")
                            // ROLE_은 붙이면 안 된다. hasAnyRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
                            .hasAnyRole("ADMIN", "USER");

                    authorizeRequests.anyRequest().permitAll();
                })

                .formLogin((formLogin) -> {
                    *//* 권한이 필요한 요청은 해당 url로 리다이렉트 *//*
                    formLogin.loginPage("/login");
                })

                .build();
    }*/
}
