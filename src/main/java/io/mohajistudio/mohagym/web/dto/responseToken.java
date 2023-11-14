package io.mohajistudio.mohagym.web.dto;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class responseToken {

    private String accessToken;

    private String refreshToken;


}
