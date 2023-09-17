package io.mohajistudio.mohagym.web.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class responseMember {

    public responseMember() {
    }

    @Builder
    public responseMember(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;


}
