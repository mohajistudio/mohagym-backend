package io.mohajistudio.mohagym.configuration;

import io.mohajistudio.mohagym.provider.security.JwtAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/secret/jwt-secrets.properties")
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtAuthTokenProvider jwtProvider(){
        return new JwtAuthTokenProvider(secret);
    }
}
