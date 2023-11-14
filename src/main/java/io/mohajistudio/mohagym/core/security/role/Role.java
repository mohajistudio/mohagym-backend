package io.mohajistudio.mohagym.core.security.role;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    ADMIN("ROLE_ADMIN","관리자 권한"),
    USER("ROLE_USER","사용자 권한"),
    UNKNOWN("UNKNOWN","알 수 없는 권한");

    private String code;
    private String description;

    Role(String code, String description){
        this.code = code;
        this.description = description;
    }
    //코드를 입력받아 일치하는 열거형 상수를 반환하는 매서드// 일치하는 것이 없으면 UNKNOWN 반환
    public static  Role findByCode(String code){
        return Arrays.stream(Role.values())
                .filter(r-> r.getCode().equals(code))
                .findAny()
                .orElse(UNKNOWN);
    }
}
