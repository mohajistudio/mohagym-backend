package io.mohajistudio.mohagym.web.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class requestMember {
    private String userId;
    private String password;
}




