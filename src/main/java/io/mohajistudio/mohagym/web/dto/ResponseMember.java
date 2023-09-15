package io.mohajistudio.mohagym.web.dto;

import lombok.Builder;
import lombok.Data;

public class ResponseMember {
    //RequestMember의 주석 참조
    @Builder
    @Data
    public static class Token{
        private String accessToken;
        private String refreshToken;
    }
}
