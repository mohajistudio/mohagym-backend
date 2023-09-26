package io.mohajistudio.mohagym.web.dto;


import lombok.Builder;
import lombok.Getter;



//필드가 한 개인 dto는 json 파싱을 위해서 기본 생성자가 필요하기에
//@data 어노테이션을 사용하지 않고 직접 생성자를 만들어 줘야 한다
@Getter
public class requestUserId {

    public requestUserId() {
    }
    @Builder
    public requestUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

}
