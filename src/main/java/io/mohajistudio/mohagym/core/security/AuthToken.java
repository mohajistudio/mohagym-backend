package io.mohajistudio.mohagym.core.security;
//제네릭 인터페이스
public interface AuthToken<T>{
    //클레임 정보가 존재하는지 확인
    boolean validate();

    //클레임 정보 반환
    //클레임: 사용자 ID, 권한, 토큰의 유효 기간 및 기타 사용자 관련 정보
    //사용자식별, 인가 및 권한 확인, 토큰 검증 시 사용
    T getClaims();
}
