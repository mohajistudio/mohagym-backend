package io.mohajistudio.mohagym.core.security;


import java.security.SecureRandom;
import java.util.Base64;

// resources/secret/jwt-secrets.properties
//jwt.secret=
//난수 생성 코드
public class makeRandomKey {
    public static void main(String[] args) {
        // 256비트(32바이트) 길이의 안전한 난수 생성
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);

        // 생성된 난수를 Base64로 인코딩하여 문자열로 출력
        String jwtSecretKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated JWT Secret Key: " + jwtSecretKey);
    }
}
