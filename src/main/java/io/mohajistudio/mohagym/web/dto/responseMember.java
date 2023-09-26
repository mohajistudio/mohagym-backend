package io.mohajistudio.mohagym.web.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Builder
@Data
public class responseMember {

    private String accessToken;

    private String refreshToken;


}
