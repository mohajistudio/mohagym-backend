package io.mohajistudio.mohagym.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class requestToken {


    private String accessToken;

    private String refreshToken;


}
